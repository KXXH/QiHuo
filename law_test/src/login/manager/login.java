package login.manager;

import javax.servlet.http.HttpSession;

import user.manager.User;
import utils.*;
import permission.manager.*;

import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/16.
 */
public class login extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try{
            String wechat_id=request.getParameter("wechat_id");
            String wechat_name=request.getParameter("wechat_name");
            request.getSession().setAttribute("user_wechat_name",wechat_name);
            request.getSession().setAttribute("uuid",request.getParameter("uuid"));
            System.out.println("login:cookie="+cookieManager.getCookieByName(request,"uuid"));
            if(wechat_id!=null&&wechat_id.length()>0){
                User user=User.findUser(wechat_id,"WeChatId");
                if(user==null){
                    request.getRequestDispatcher("login_with_wechat.html").forward(request, response);
                    return;
                }else{
                    request.getRequestDispatcher("login_with_wechat_confirm.html?wechat_name="+wechat_name).forward(request,response);
                    return;
                }
            }
            HttpSession session=request.getSession();
            String token=tokenExtractor.extractToken(request);
            User user=tokenChecker.tokenToUser(token);
            if(user!=null){

                response.sendRedirect("index_beta.html");
            }
            else if(session.getAttribute("captcha_flag")==null||(boolean)session.getAttribute("captcha_flag")==false){
                request.getRequestDispatcher("login_beta.html").forward(request, response);
            }else{
                loginRecorder.loginWithToken(request);
                request.getRequestDispatcher("login_with_captcha.html").forward(request, response);
            }
        }catch(IllegalStateException ignored){
            exceptionManager.logException(ignored,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            request.getRequestDispatcher("login_beta.html").forward(request, response);
        }
    }

}
