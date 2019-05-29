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
public class deleteAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("执行了POST--user.manager");
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String token = tokenExtractor.extractToken(request);
        System.out.println("token="+token);
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        try {
            User user=User.findUserById(userId);
            if(user==null){
                sendManager.sendSimpleErrorJSON(response);
                return;
            }
            if(!user.is_Editable(tokenChecker.tokenToUser(token))){
                sendManager.sendDefaultPermissionError(response);
            }else{
                user.delUser();
                sendManager.sendSimpleOKJSON(response);
            }

        } catch (SQLException e) {
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
