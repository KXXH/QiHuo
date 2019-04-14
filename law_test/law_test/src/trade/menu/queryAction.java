package trade.menu;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by zjm97 on 2019/3/27.
 */
public class queryAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("执行了post");
        String tocken = request.getParameter("tocken");
        String passWd;
        int userId;
        String userType="normal";
        List jsonList = new ArrayList();
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
        }


        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            String sql="SELECT * FROM tbl_tockeninfo WHERE tockenValue=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,tocken);
            ResultSet rs=ptmt.executeQuery();
            if(!rs.next()){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("error","wrong username or password");
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
            }
            else{
                String TTL = rs.getString("TTL");
                java.util.Date date = new java.util.Date();
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    date = sdf.parse(TTL);
                }
                catch (ParseException e)
                {
                    System.out.println(e.getMessage());
                }
                if(date.getTime()<new java.util.Date().getTime()){
                    System.out.println("tocken已过期");
                    System.out.printf("ttl=%d",date.getTime());
                    System.out.printf("time=%d",new java.util.Date().getTime());
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
                userId = rs.getInt("UserId");
                //userType = rs.getString("role_id");
            }

            sql="SELECT * FROM tbl_userinfo WHERE userId=?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1,userId);
            rs = ptmt.executeQuery();
            if(rs.next()){
                userType = rs.getString("role_id");
            }
            sql="SELECT * FROM tbl_law_menu WHERE role_id=?";
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userType);
            rs=ptmt.executeQuery();
            while(rs.next()){
                List list = new ArrayList();
                list.add(rs.getString("role_id"));
                list.add(rs.getString("href"));
                list.add(rs.getString("caption"));
                list.add(rs.getInt("parent_id"));
                jsonList.add(list);
            }

        }catch(SQLException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("aaData",jsonList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        response.setContentType("application/json; charset=UTF-8");
        try{
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }
}
