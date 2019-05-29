package user.manager;

import com.octo.captcha.module.servlet.image.SimpleImageCaptchaServlet;
import org.json.JSONException;
import org.json.JSONObject;
import utils.exceptionManager;
import utils.sendManager;
import utils.tokenGenerator;

import javax.mail.Session;
import javax.servlet.http.HttpSession;
import java.security.GeneralSecurityException;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/22.
 */
public class userRegisterAction extends javax.servlet.http.HttpServlet {
    /**
     * 错误码对照表：
     * 11：验证码错误
     * 20：用户名或邮箱重复
     * @param request
     * @param response
     * @throws javax.servlet.ServletException
     * @throws java.io.IOException
     */
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String username=request.getParameter("username");
        String password=request.getParameter("password");
        String email=request.getParameter("email");
        String phone=request.getParameter("phone");
        String jcaptcha=request.getParameter("jcaptcha");
        boolean captchaPassed= SimpleImageCaptchaServlet.validateResponse(request,jcaptcha);
        if(!captchaPassed){
            JSONObject jsonObject=new JSONObject();
            try {
                jsonObject.put("status","error");
                jsonObject.put("error","验证码错误");
                jsonObject.put("code",11);
                sendManager.sendJSON(response,jsonObject);
            } catch (JSONException e) {
                exceptionManager.logException(e,this);
                e.printStackTrace();
            }
            return;
        }
        try {
            User user=User.addUser(username,password,email,phone,"undifined","unchecked");
            if(user==null){
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("status","error");
                jsonObject.put("error","用户名或邮箱已存在!");
                jsonObject.put("code",20);
                sendManager.sendJSON(response,jsonObject);
            }else{
                sendManager.sendSimpleOKJSON(response);
                String token= tokenGenerator.getAndStoreToken(user.getUserName());
                HttpSession session=request.getSession();
                session.setAttribute("token",token);
                String requestUrl = request.getScheme() //当前链接使用的协议
                        +"://" + request.getServerName()//服务器地址
                        + ":" + request.getServerPort() //端口号
                        + request.getContextPath(); //应用名称，如果应用名称为
                mailService.sendMail.sendEnableAccountEmail(user.getEmail(),requestUrl+"/enableAccount.html?"+token);
            }
        } catch (SQLException | JSONException | GeneralSecurityException e) {
            exceptionManager.logException(e,this);
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
