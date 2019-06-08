package transaction.bmma.delet;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.changeMoney;
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


@WebServlet(name = "bmmadeleteAction")
public class bmmadeleteAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String orderid = request.getParameter("OrderId");
            System.out.println("前端页面传过来的ID是："+orderid+"<br>");
            request.setCharacterEncoding("UTF-8");
            JSONObject jsonObject = new JSONObject();
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException classnotfoundexception) {
                classnotfoundexception.printStackTrace();
            }
            Connection conn = null;
            try {
                conn = dbOpener.getDB();
                conn.setAutoCommit(false);
                Statement statement = conn.createStatement();
                System.out.println("Connect Database Ok！！！<br>");
                String sql1 = "select * from tbl_bm WHERE OrderId='"+orderid+"'";
                ResultSet rs = statement.executeQuery(sql1);
                int userid = 0;
                int quantity = 0;
                double bunitprice = 0;
                double money=0;
                if(rs.next())
                {
                    userid = rs.getInt("UserId");;
                    quantity =rs.getInt("Quantity");
                    bunitprice = rs.getDouble("BUnitPrice");

                }
                money=quantity*bunitprice;
                changeMoney.increaseMoney(response,userid,money);

                String sql = "delete from tbl_bm where OrderId=" + orderid;
                System.out.println("sql是：：：：："+sql);
                statement.executeUpdate(sql);
                System.out.println(sql);
                conn.commit();//提交事务
                statement.close();
                conn.close();
                System.out.println("Database Closed！！！<br>");
            } catch (SQLException sqlexception) {
                sqlexception.printStackTrace();
                try {
                    jsonObject.put("status",0);
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
                return;
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

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

