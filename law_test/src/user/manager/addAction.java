package user.manager;

import org.json.JSONException;
import org.json.JSONObject;
import utils.tokenChecker;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Objects;
import java.util.Date;
import utils.*;
import permission.manager.*;
/**
 * Created by zjm97 on 2019/4/18.
 */
public class addAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("执行了POST--user.add");
        String username = request.getParameter("username");
        String password=request.getParameter("password");
        System.out.println("username="+username);
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String wechat_id=request.getParameter("wechat_id");
        String token = tokenExtractor.extractToken(request);
        String role_id=request.getParameter("role_id");
        System.out.println("token="+token);
        String user_role= tokenChecker.checkToken(token);
        User user=tokenChecker.tokenToUser(token);
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        System.out.println("user_role="+user_role);
        try{
            User userNew=User.addUser(username,password,email,phone,wechat_id,role_id);
            if(userNew==null){
                sendManager.sendSimpleErrorJSON(response);
            }
            sendManager.sendSimpleOKJSON(response);
        }catch (SQLException e){
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

}
