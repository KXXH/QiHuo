package warehouse.change;

import org.json.JSONException;
import org.json.JSONObject;
import tool.tool.User;
import tool.tool.tokenChecker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;


@WebServlet(name = "changeAction")
public class changeAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



        String stockid = request.getParameter("StockId");
        //String stockname = request.getParameter("StockName");
        String quantity = request.getParameter("Quantity");
        String bunitprice = request.getParameter("BUnitPrice");
        String tocken = request.getParameter("Cookie");
        System.out.println(tocken);

        User user = tokenChecker.tokenToUser(tocken);

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
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            System.out.println("准备statement。");
            Statement statement = conn.createStatement();
            System.out.println("已经链接上数据库！");

            String sql1 = "update tbl_userrealwh set Quantity='" + quantity + "' WHERE UserId='"+user.getUserId()+"' and StockId='"+stockid+"'";
            System.out.println("sql1:"+sql1);
            statement.executeUpdate(sql1);

            String sql = "update tbl_userwh set Quantity='" + quantity
                        + "',BUnitPrice='" + bunitprice+ "' WHERE UserId='"+user.getUserId()+"' and StockId='"+stockid+"'";
            System.out.println("sql"+sql);
            System.out.println("即将执行的SQL语句是：" + sql);
            statement.executeUpdate(sql);
            statement.close();
            conn.close();

            System.out.println("操作数据完毕，关闭了数据库！");

        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();

        }

        System.out.println("页面执行完毕！");
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
