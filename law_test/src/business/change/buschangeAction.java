package business.change;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.dbOpener;
import utils.tokenChecker;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;


@WebServlet(name = "buschangeAction")
public class buschangeAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String orderid = request.getParameter("OrderId");
        String user_id = request.getParameter("UserId");
        String user_name = request.getParameter("UserName");
        String stock_id = request.getParameter("Stockid");
        String stock_name = request.getParameter("StockName");
        String quan_tity = request.getParameter("Quantity");
        String bunit_price = request.getParameter("BUnitPrice").toString();
        //String tocken = request.getParameter("Cookie");
        //System.out.println(tocken);

        //User user = tokenChecker.tokenToUser(tocken);

        request.setCharacterEncoding("UTF-8");
        System.out.println("页面传递过来的数据获取完毕");

        //开始连接数据库，需要先把mysql-connector-java-5.0.4-bin.jar和json.jar拷贝到ROOT/WEB-INF/lib下
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
        }
        System.out.println("加载了JDBC驱动");
        JSONObject jsonObject = new JSONObject();
        //然后链接数据库，开始操作数据表
        try {
            Connection conn = dbOpener.getDB();
            System.out.println("准备statement。");
            Statement statement = conn.createStatement();
            System.out.println("已经链接上数据库！");


            String sql3 = "select * from tbl_userwh WHERE OrderId=?";
            //String sql3 = "select * from tbl_userrealwh WHERE UserName=? and StockId=? and StockName=?";
            System.out.println("即将执行的SQL3语句是：" + sql3);

            System.out.println(stock_id);
            PreparedStatement ps;
            try {
                ps = conn.prepareStatement(sql3);    //实例化PreparedStatement对象
                ps.setString(1, orderid);
                ResultSet rs = ps.executeQuery();   //执行预处理语句获取查询结果集
                System.out.println("执行完毕，逐条显示<br>");
                //如果查询有结果，则循环显示查询出来的记录
                while (rs.next()) {
                    if (user_id.length() == 0) {
                        user_id = rs.getString("UserId");
                    }
                    if (user_name.length() == 0) {
                        user_name = rs.getString("UserName");
                    }
                    if (stock_id.length() == 0) {
                        stock_id = rs.getString("StockId");
                    }
                    if (stock_name.length() == 0) {
                        stock_name = rs.getString("StockName");
                    }
                    if (quan_tity.length() == 0) {
                        quan_tity = rs.getString("Quantity");
                    }
                    if (bunit_price.length() == 0) {
                        bunit_price = rs.getString("BUnitPrice");
                    }

                }
                int userid = Integer.parseInt(user_id);
                int stockid = Integer.parseInt(stock_id);
                int quantity = Integer.parseInt(quan_tity);
                double bunitprice = Double.valueOf(bunit_price.toString());

                System.out.println(orderid);
                System.out.println(userid);
                System.out.println(user_name);
                System.out.println(stockid);
                System.out.println(stock_name);
                System.out.println(quantity);
                System.out.println(bunitprice);


                String sql = "update tbl_userwh set UserId='" + userid + "',UserName='" + user_name + "',StockId='" + stockid + "',StockName='" + stock_name + "',Quantity='" + quantity + "',BUnitPrice='" + bunitprice + "' WHERE OrderId='" + orderid + "'";
                System.out.println("sql" + sql);
                System.out.println("即将执行的SQL语句是：" + sql);
                statement.executeUpdate(sql);
                statement.close();
                conn.close();

                System.out.println("操作数据完毕，关闭了数据库！");

            } catch (SQLException sqlexception) {
                sqlexception.printStackTrace();

            }

            System.out.println("页面执行完毕！");
        } catch (SQLException e) {
            e.printStackTrace();
            e.printStackTrace();
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
