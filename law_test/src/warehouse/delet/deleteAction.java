package warehouse.delet;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.dbOpener;
import utils.sendManager;
import utils.tokenChecker;
import utils.tokenExtractor;

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
        String tocken= tokenExtractor.extractToken(request);
        User user = tokenChecker.tokenToUser(tocken);
        String a =null;
        int quantity = Integer.parseInt(request.getParameter("Quantity"));
        System.out.println("数量是："+quantity);
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
                if(quantity==0)
                {
                    String sql = "delete from tbl_userrealwh where StockId= '"+stockid+"' and UserId='"+user.getUserId()+"'";
                    statement.executeUpdate(sql);
                    System.out.println(sql);
                }
                else
                {
                    sendManager.sendErrorJSONWithMsgAndCode(response,a,1);
                    return;
                }
                statement.close();
                conn.close();
                System.out.println("Database Closed！！！<br>");
            } catch (SQLException sqlexception) {
                sqlexception.printStackTrace();
                sendManager.sendSimpleErrorJSON(response);
            }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

