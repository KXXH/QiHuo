package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;
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
        String user_role= utils.tokenChecker.checkToken(token);
        System.out.println("user_role="+user_role);
        if(!Objects.equals(user_role, "admin")){
            sendManager.sendSimpleErrorJSON(response);
            return;
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            String sql = "UPDATE tbl_userinfo SET UserName=?,Email=?,Phone=?,WeChatId=?,role_id=? WHERE UserId=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,username);
            ptmt.setString(2,email);
            ptmt.setString(3,phone);
            ptmt.setString(4,wechat_id);
            ptmt.setString(5,role_id);
            ptmt.setInt(6,userId);
            ptmt.execute();
            sendManager.sendSimpleOKJSON(response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}