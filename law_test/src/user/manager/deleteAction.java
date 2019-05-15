package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/4/17.
 */
public class deleteAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("执行了POST--user.manager");
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String token = request.getParameter("token");
        System.out.println("token="+token);
        String user_role=tokenChecker.checkToken(token);
        System.out.println("user_role="+user_role);
        if(!Objects.equals(user_role, "admin")){
            try{
                JSONObject json = new JSONObject();
                json.put("status","error");
                response.setContentType("application/json; charset=UTF-8");
                try{
                    response.getWriter().print(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                return;
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            String sql = "DELETE FROM tbl_userinfo WHERE UserId=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1,userId);
            ptmt.execute();
            try{
                JSONObject json = new JSONObject();
                json.put("status","ok");
                response.setContentType("application/json; charset=UTF-8");
                try{
                    response.getWriter().print(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                return;
            }catch(JSONException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
