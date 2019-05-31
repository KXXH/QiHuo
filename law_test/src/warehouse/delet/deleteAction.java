package warehouse.delet;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
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


@WebServlet(name = "deleteAction")
public class deleteAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String stockid = request.getParameter("StockId");
            System.out.println("前端页面传过来的ID是："+stockid+"<br>");
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
                String sql = "delete from tbl_userrealwh where StockId=" + stockid;
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

