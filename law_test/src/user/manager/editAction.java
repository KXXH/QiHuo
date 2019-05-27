package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import permission.manager.permissionChecker;
import utils.*;
/**
 * Created by zjm97 on 2019/4/17.
 */
public class editAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("执行了POST--user.manager");
        String username = request.getParameter("username");
        int userId = Integer.parseInt(request.getParameter("user_id"));
        System.out.println("username="+username);
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String wechat_id = request.getParameter("wechat_id");
        String role_id = request.getParameter("role_id");
        String token = tokenExtractor.extractToken(request);
        System.out.println("token="+token);
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        User operateUser = tokenChecker.tokenToUser(token);
        User targetUser = User.findUserById(userId);
        if(targetUser==null){
            sendManager.sendSimpleErrorJSON(response);
        }else{
            try {
                if(!targetUser.setUserName(username)){
                    sendManager.sendSimpleErrorJSON(response);
                    return;
                }
                if(!targetUser.setEmail(email)){
                    sendManager.sendSimpleErrorJSON(response);
                    return;
                }
                targetUser.setPhone(phone);
                targetUser.setWechatId(wechat_id);
                if(!targetUser.setRole_id(role_id,operateUser)){
                    sendManager.sendDefaultPermissionError(response);
                }
                sendManager.sendSimpleOKJSON(response);
            } catch (SQLException e) {
                exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                e.printStackTrace();
            }
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
