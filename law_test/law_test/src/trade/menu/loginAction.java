package trade.menu;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.Base64;
/**
 * Created by zjm97 on 2019/3/29.
 */
@WebServlet(name = "loginAction")
public class loginAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("执行了post");
        String userName = request.getParameter("UserName");
        String password = request.getParameter("PassWd");
        String rememberPassword = request.getParameter("rememberPassword");
        String tocken = request.getParameter("tocken");
        System.out.println("remember="+rememberPassword);
        String userType = "normal";

        List jsonList = new ArrayList();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.printStackTrace();
        }


        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=Zjm,,971014&useUnicode=true&characterEncoding=UTF-8");
            System.out.println("连接了数据库");
            if(tocken.length()>0){
                String sql = "SELECT * FROM tbl_tockeninfo WHERE tockenValue=?";
                PreparedStatement ptmt = conn.prepareStatement(sql);
                ptmt.setString(1,tocken);
                ResultSet rs = ptmt.executeQuery();
                if(!rs.next()){
                    System.out.println("没有找到tocken");
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status", "error");
                    jsonObject.put("error", 2);
                    response.setContentType("application/json; charset=UTF-8");
                    try {
                        response.getWriter().print(jsonObject);
                        response.getWriter().flush();
                        response.getWriter().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }

                String TTL = rs.getString("TTL");
                Date date = new Date();
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = sdf.parse(TTL);
                }
                catch (ParseException e)
                {
                    System.out.println(e.getMessage());
                }
                if(date.getTime()<new Date().getTime()){
                    System.out.println("tocken已过期");
                    System.out.printf("ttl=%d",date.getTime());
                    System.out.printf("time=%d",new Date().getTime());
                    sql = "DELETE FROM tbl_tockeninfo WHERE tockenValue=?";
                    ptmt = conn.prepareStatement(sql);
                    ptmt.setString(1,tocken);
                    ptmt.execute();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("status", "error");
                    jsonObject.put("error", 3);
                    response.setContentType("application/json; charset=UTF-8");
                    try {
                        response.getWriter().print(jsonObject);
                        response.getWriter().flush();
                        response.getWriter().close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", "success");
                jsonObject.put("tocken", tocken);
                response.setContentType("application/json; charset=UTF-8");
                try {
                    response.getWriter().print(jsonObject);
                    response.getWriter().flush();
                    response.getWriter().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;

            }
            String sql = "SELECT * FROM tbl_userinfo WHERE UserName=? AND PassWd=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, userName);
            ptmt.setString(2, password);
            ResultSet rs = ptmt.executeQuery();
            if (!rs.next()) {
                System.out.println("没有找到用户");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("status", "error");
                jsonObject.put("error", 1);
                response.setContentType("application/json; charset=UTF-8");
                try {
                    response.getWriter().print(jsonObject);
                    response.getWriter().flush();
                    response.getWriter().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            userType = rs.getString("role_id");
            int userId = rs.getInt("UserId");
            Date date = new Date();
            tocken = Base64.getEncoder().encodeToString((userName+String.valueOf(date.getTime())).getBytes("utf-8"));
            System.out.println("tocken="+tocken);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","success");
            jsonObject.put("tocken",tocken);
            sql = "SELECT * FROM tbl_tockeninfo WHERE UserName=?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userName);
            rs = ptmt.executeQuery();
            if(rs.next()) {
                sql = "UPDATE tbl_tockeninfo SET tockenValue=?, TTL=? WHERE UserName=? AND UserId=?";
            }
            else{
                sql="INSERT INTO tbl_tockeninfo (tockenValue, TTL,UserName,UserId) VALUES(?,?,?,?)";
            }
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, tocken);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date currentDate = new java.util.Date();
            long timeStamp = currentDate.getTime()+5*60000;
            System.out.println(timeStamp);
            if(Objects.equals(rememberPassword, "true")){
                timeStamp+=60*1000*60*24*10;
            }
            ptmt.setString(2,sdf.format(new java.util.Date(timeStamp)));
            System.out.println("TTL时间是"+sdf.format(new java.util.Date(timeStamp)));
            //ptmt.setDate(2, new java.sql.Date(new java.util.Date().getTime()));
            ptmt.setString(3,userName);
            ptmt.setInt(4,userId);
            ptmt.execute();
            response.setContentType("application/json; charset=UTF-8");
            try {
                response.getWriter().print(jsonObject);
                response.getWriter().flush();
                response.getWriter().close();
                System.out.println("发送回复成功");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
