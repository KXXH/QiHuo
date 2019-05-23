package business.delet;

import org.json.JSONException;
import org.json.JSONObject;
import utils.dbOpener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "busdeleteAction")
public class busdeleteAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        String orderid = request.getParameter("OrderId");
            System.out.println("前端页面传过来的ID是："+orderid+"<br>");
            request.setCharacterEncoding("UTF-8");
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException classnotfoundexception) {
                classnotfoundexception.printStackTrace();
            }
            try {
                Connection conn = dbOpener.getDB();
                Statement statement = conn.createStatement();
                System.out.println("Connect Database Ok！！！<br>");
                String sql = "delete from tbl_userwh where OrderId=" + orderid;
                statement.executeUpdate(sql);
                System.out.println(sql);
                statement.close();
                conn.close();
                System.out.println("Database Closed！！！<br>");
            } catch (SQLException sqlexception) {
                sqlexception.printStackTrace();
       
            }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
