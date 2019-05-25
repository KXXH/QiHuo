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
        String userid = request.getParameter("UserId");
        String username = request.getParameter("UserName");
        String stockid = request.getParameter("StockId");
        String stockname = request.getParameter("StockName");
        String quantity = request.getParameter("Quantity");
        String bunitprice = request.getParameter("BUnitPrice");
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

        //然后链接数据库，开始操作数据表
        try {
            Connection conn = dbOpener.getDB();
            System.out.println("准备statement。");
            Statement statement = conn.createStatement();
            System.out.println("已经链接上数据库！");


            String sql3 = "select * from tbl_userwh WHERE OrderId=?";
            //String sql3 = "select * from tbl_userrealwh WHERE UserName=? and StockId=? and StockName=?";
            System.out.println("即将执行的SQL3语句是：" + sql3);

            System.out.println(stockid);
            PreparedStatement ps;
            try {
                ps = conn.prepareStatement(sql3);    //实例化PreparedStatement对象
                ps.setString(1, orderid);
                ResultSet rs = ps.executeQuery();   //执行预处理语句获取查询结果集
                System.out.println("执行完毕，逐条显示<br>");
                //如果查询有结果，则循环显示查询出来的记录
                while (rs.next()) {
                    if (userid.length() == 0) {
                        userid = rs.getString("UserId");
                    }
                    if (username.length() == 0) {
                        username = rs.getString("UserName");
                    }
                    if (stockid.length() == 0) {
                        stockid = rs.getString("StockId");
                    }
                    if (stockname.length() == 0) {
                        stockname = rs.getString("StockName");
                    }
                    if (quantity.length() == 0) {
                        quantity = rs.getString("Quantity");
                    }
                    if (bunitprice.length() == 0) {
                        bunitprice = rs.getString("BUnitPrice");
                    }

                }
                System.out.println(orderid);
                System.out.println(userid);
                System.out.println(username);
                System.out.println(stockid);
                System.out.println(stockname);
                System.out.println(quantity);
                System.out.println(bunitprice);


                String sql = "update tbl_userwh set UserId='" + userid + "',UserName='" + username + "',StockId='" + stockid + "',StockName='" + stockname + "',Quantity='" + quantity + "',BUnitPrice='" + bunitprice + "' WHERE OrderId='" + orderid + "'";
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
        }
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
