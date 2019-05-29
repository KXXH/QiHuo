package login.manager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import java.util.Date;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import org.json.JSONException;
import org.json.JSONObject;
import org.omg.CORBA.Request;
import user.manager.User;

import java.util.Base64;
import utils.tokenChecker;
import utils.tokenGenerator;
import utils.*;
/**
 * Created by zjm97 on 2019/3/29.
 */
@WebServlet(name = "loginAction")
public class loginAction extends HttpServlet {
    //POST返回错误码对照:
    //1:用户名或密码错误
    //2:token不存在
    //10:尝试次数过多，需要验证码
    //11:验证码错误
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("执行了post");
        HttpSession session = request.getSession(true);
        String userName = request.getParameter("UserName");
        String password = request.getParameter("PassWd");
        String rememberPassword = request.getParameter("rememberPassword");
        String token = tokenExtractor.extractToken(request);
        System.out.println("remember="+rememberPassword);
        Cookie tokenCookie=cookieManager.getCookieByName(request,"token");
        String userCaptchaResponse = request.getParameter("jcaptcha");
        System.out.println("session id="+session.getId());
        if(session==null){
            System.out.println("session is null!");
        }
        else{
            System.out.println("session is not null!");
        }
        boolean captcha_flag;
        if(session.getAttribute("captcha_flag")==null){
            captcha_flag=false;
        }else{
            captcha_flag=(boolean)session.getAttribute("captcha_flag");
        }
        boolean captchaPassed =true;
        if(captcha_flag){
            captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
        }
        if(captcha_flag&&!captchaPassed&&userCaptchaResponse!=null&&userCaptchaResponse.length()>0){
            JSONObject jsonObject=new JSONObject();
            System.out.println("验证码错误!!");
            try {
                jsonObject.put("error",11);
                jsonObject.put("status","error");
            } catch (JSONException e) {
                exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                e.printStackTrace();
            }
            sendManager.sendJSON(response,jsonObject);
            return;
        }else if(captcha_flag&&!captchaPassed&&(userCaptchaResponse==null||userCaptchaResponse.length()==0)){
            JSONObject jsonObject=new JSONObject();
            System.out.println("未填写验证码!!");
            try {
                jsonObject.put("error",10);
                jsonObject.put("status","error");
            } catch (JSONException e) {
                exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                e.printStackTrace();
            }
            sendManager.sendJSON(response,jsonObject);
            return;
        }


        try {
            System.out.println("连接了数据库");
            if(token!=null&&token.length()>0){
                User user=tokenChecker.tokenToUser(token);
                if(user==null){
                    System.out.println("没有找到tocken");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status", "error");
                    jsonObject.put("error", 2);
                    request.getSession().invalidate();
                    utils.sendManager.sendJSON(response,jsonObject);
                    return;
                }
                loginRecorder.loginWithToken(request);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", "success");
                jsonObject.put("tocken", token);
                utils.sendManager.sendJSON(response,jsonObject);
                return;
            }
            User user=User.login(userName,password);
            if (user==null) {
                System.out.println("没有找到用户");
                JSONObject jsonObject = new JSONObject();

                int count;
                if(session.getAttribute("attempt_count")==null){
                    count=0;
                }else{
                    count=(int)session.getAttribute("attempt_count");
                }
                System.out.println("count="+String.valueOf(count));
                if(count==0){
                    session.setAttribute("attempt_count",1);
                    jsonObject.put("status", "error");
                    jsonObject.put("error", 1);
                    sendManager.sendJSON(response,jsonObject);
                }else if(count<3||count>3){
                    session.setAttribute("attempt_count",count+1);
                    jsonObject.put("status", "error");
                    jsonObject.put("error", 1);
                    sendManager.sendJSON(response,jsonObject);
                }else if(count==3){
                    jsonObject.put("status","error");
                    jsonObject.put("error",10);//captcha required
                    session.setAttribute("captcha_flag",true);
                    sendManager.sendJSON(response,jsonObject);
                    session.setAttribute("attempt_count",count+1);
                    return;
                }
                return;
            }
            loginRecorder.loginWithPassword(user,request);
            token=tokenGenerator.getAndStoreToken(userName,Objects.equals(rememberPassword, "true"));
            System.out.println("tocken="+token);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","success");
            jsonObject.put("tocken",token);
            session.invalidate();
            session= request.getSession();
            session.setAttribute("token",token);
            session.setAttribute("captcha_flag",false);
            session.setAttribute("attempt_count",0);

            if(Objects.equals(rememberPassword, "true")){
                //cookieManager.addCookie(request,response,"token",token,30*24*3600);
                session.setMaxInactiveInterval(30*24*3600);
            }
            utils.sendManager.sendJSON(response,jsonObject);
            System.out.println("发送回复成功");
        } catch (JSONException e) {
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

}
