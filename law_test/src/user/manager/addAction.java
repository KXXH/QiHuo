package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Date;
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
        String token = request.getParameter("token");
        String role_id=request.getParameter("role_id");
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

        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            String sql="INSERT INTO tbl_userinfo (UserName,Passwd,Email,Phone,WeChatId,CreateAt,role_id) VALUES(?,?,?,?,?,?,?)";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            System.out.println("SQL语句是:"+sql);
            ptmt.setString(1,username);
            ptmt.setString(2,password);
            ptmt.setString(3,email);
            ptmt.setString(4,phone);
            ptmt.setString(5,wechat_id);
            ptmt.setString(7,role_id);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date ddate=new java.util.Date();
            String sdate=simpleDateFormat.format(ddate);
            ptmt.setString(6,sdate);
            System.out.println("真正执行的SQL是"+ptmt.toString());
            ptmt.execute();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","ok");
            response.setContentType("application/json; charset=UTF-8");
            try{
                response.getWriter().print(jsonObject);
                response.getWriter().flush();
                response.getWriter().close();
            }
            catch(IOException e){
                e.printStackTrace();
            }
            return;

        }catch (SQLException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
