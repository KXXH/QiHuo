package utils;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by zjm97 on 2019/5/16.
 * 响应发送工具类，可以发送简单的JSON信息，也可以发送简单的格式化错误信息和成功信息
 */
public class changeMoney {


    public static HttpServletResponse reduceMoney(HttpServletResponse response,int userid,double money) throws ServletException, IOException {
        JSONObject jsonObject=new JSONObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        System.out.println("加载了JDBC驱动");

        //然后链接数据库，开始操作数据表
        try {
            Connection conn = dbOpener.getDB();
            System.out.println("准备statement。");
            Statement statement = conn.createStatement();
            System.out.println("已经链接上数据库！");


            String sql2 = "update tbl_userinfo set Balance=Balance-'"+money+"' where UserId = '"+userid+"'";
            System.out.println("即将执行的SQL语句是：" + sql2);
            statement.executeUpdate(sql2);

            statement.close();
            conn.close();

            System.out.println("操作数据完毕，关闭了数据库！");

        } catch (SQLException e) {
            try {
                jsonObject.put("status","0");
                try {
                    response.getWriter().print(jsonObject);
                    response.getWriter().flush();
                    response.getWriter().close();
                } catch (IOException et) {
                    et.printStackTrace();
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
        try {
            jsonObject.put("status","1");
            response.setContentType("application/json; charset=UTF-8");
            try {
                response.getWriter().print(jsonObject);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("页面执行完毕！");
        return response;
    }

    public static HttpServletResponse increaseMoney(HttpServletResponse response,int userid,double money) throws ServletException, IOException {
        JSONObject jsonObject=new JSONObject();

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        System.out.println("加载了JDBC驱动");

        //然后链接数据库，开始操作数据表
        try {
            Connection conn = dbOpener.getDB();
            System.out.println("准备statement。");
            Statement statement = conn.createStatement();
            System.out.println("已经链接上数据库！");


            String sql2 = "update tbl_userinfo set Balance=Balance+'"+money+"' where UserId = '"+userid+"'";
            System.out.println("即将执行的SQL语句是：" + sql2);
            statement.executeUpdate(sql2);

            statement.close();
            conn.close();

            System.out.println("操作数据完毕，关闭了数据库！");

        } catch (SQLException e) {
            try {
                jsonObject.put("status","0");
                try {
                    response.getWriter().print(jsonObject);
                    response.getWriter().flush();
                    response.getWriter().close();
                } catch (IOException et) {
                    et.printStackTrace();
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
        try {
            jsonObject.put("status","1");
            response.setContentType("application/json; charset=UTF-8");
            try {
                response.getWriter().print(jsonObject);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("页面执行完毕！");
        return response;
    }
}
