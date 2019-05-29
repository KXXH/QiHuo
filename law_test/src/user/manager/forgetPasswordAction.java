package user.manager;
import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import mailService.sendMail;
import org.json.JSONException;
import org.json.JSONObject;
import utils.tokenGenerator;

import java.io.IOException;
import java.security.GeneralSecurityException;
import utils.*;
/**
 * Created by zjm97 on 2019/5/15.
 */
public class forgetPasswordAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String email=request.getParameter("email");
        User user=User.findUser(email,"Email");
        String userCaptchaResponse = request.getParameter("jcaptcha");
        boolean captchaPassed = SimpleImageCaptchaServlet.validateResponse(request, userCaptchaResponse);
        if(captchaPassed){
// proceed to submit action
            JSONObject json=new JSONObject();
            if(user==null){
                try {
                    json.put("status","error");
                    json.put("error","邮箱不存在!");
                    sendManager.sendJSON(response,json);
                } catch (JSONException e) {
                    exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                    e.printStackTrace();
                }
            }
            String token= tokenGenerator.getAndStoreToken(user.getUserName(),true);
            try {
                String requestUrl = request.getScheme() //当前链接使用的协议
                        +"://" + request.getServerName()//服务器地址
                        + ":" + request.getServerPort() //端口号
                        + request.getContextPath(); //应用名称，如果应用名称为
                System.out.println("getContexePath="+request.getContextPath());
                sendMail.sendForgetPasswdURL(email,requestUrl+"/resetPassword.html?"+token);
                sendManager.sendSimpleOKJSON(response);
            } catch (GeneralSecurityException e) {
                exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                e.printStackTrace();
            }
        }else{
// return error to user
            JSONObject json=new JSONObject();

            try {
                json.put("error","验证码错误!");
                json.put("status","error");
                sendManager.sendJSON(response,json);
            } catch (JSONException e) {
                exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                e.printStackTrace();
            }
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String email=request.getParameter("email");
        User user=User.findUser(email,"Email");
        JSONObject json=new JSONObject();
        if(user==null){
            try {
                json.put("status","error");
                json.put("error","邮箱不存在!");
                response.setContentType("application/json; charset=UTF-8");
                try{
                    response.getWriter().print(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                    return;
                }
                catch(IOException e){
                    exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                e.printStackTrace();
            }
        }
        String token=tokenGenerator.getAndStoreToken(user.getUserName());
        try {
            String requestUrl = request.getScheme() //当前链接使用的协议
                    +"://" + request.getServerName()//服务器地址
                    + ":" + request.getServerPort() //端口号
                    + request.getContextPath(); //应用名称，如果应用名称为
            System.out.println("getContexePath="+request.getContextPath());
            sendMail.sendForgetPasswdURL(email,requestUrl+"/resetPassword.html?"+token);
            json.put("status","ok");
            System.out.println("forgetPasswordAction:执行到这里了");
            response.setContentType("application/json; charset=UTF-8");
            try{
                response.getWriter().print(json);
                response.getWriter().flush();
                response.getWriter().close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        } catch (GeneralSecurityException | JSONException e) {
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }
}
