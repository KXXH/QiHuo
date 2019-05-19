package trade.menu;

import javax.servlet.http.HttpSession;

/**
 * Created by zjm97 on 2019/5/16.
 */
public class login extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        HttpSession session=request.getSession();
        if(session.getAttribute("captcha_flag")==null||(boolean)session.getAttribute("captcha_flag")==false){
            request.getRequestDispatcher("login_beta.html").forward(request, response);
        }else{
            request.getRequestDispatcher("login_with_captcha.html").forward(request, response);
        }
    }
}
