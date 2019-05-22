package user.manager;
import utils.*;
/**
 * Created by zjm97 on 2019/5/22.
 */
public class checkEmailAvailable extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String email=request.getParameter("email");
        User user=User.findUser(email,"Email");
        if(user==null){
            sendManager.sendSimpleOKJSON(response);
        }else{
            sendManager.sendSimpleErrorJSON(response);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
