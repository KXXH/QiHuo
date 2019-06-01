package user.manager;
import utils.*;
/**
 * Created by zjm97 on 2019/5/22.
 */
public class checkUsernameAvailable extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String username=request.getParameter("username");
        if(!checkUserName.RegexName(username)){
            sendManager.sendErrorJSONWithMsg(response,"用户名不合法");
        }
        User user=User.findUser(username,"UserName");

        if(user==null){
            sendManager.sendSimpleOKJSON(response);
        }else{
            sendManager.sendErrorJSONWithMsg(response,"该用户名已存在!");
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
