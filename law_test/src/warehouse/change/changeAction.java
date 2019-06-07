package warehouse.change;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.changeMoney;
import utils.dbOpener;
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
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet(name = "changeAction")
public class changeAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        java.util.Date currDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(currDate);

        int userid =Integer.parseInt(request.getParameter("UserId"));
        String username = request.getParameter("UserName");
        int stockid = Integer.parseInt(request.getParameter("StockId"));
        String stockname = request.getParameter("StockName");
        int quantity = Integer.parseInt(request.getParameter("Quantity"));
        double bunitprice = Double.valueOf(request.getParameter("Quotation"));
        double money=quantity*bunitprice;
        if(request.getParameter("Quantity")!=null){
            quantity=Integer.parseInt(request.getParameter("Quantity"));
        }
        request.setCharacterEncoding("UTF-8");
        System.out.println("页面传递过来的数据获取完毕");
        JSONObject jsonObject = new JSONObject();
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

            String sql1 = "insert into tbl_bm(UserId,UserName,StockId,StockName,Quantity,BUnitPrice,CreateAt) values('"
                    + userid + "','" + username + "','"+ stockid + "','" + stockname + "','" + quantity + "','" + bunitprice + "','" + createTime + "')";
            System.out.println("即将执行的SQL1语句是：" + sql1);
            System.out.println("sql1:" + sql1);
            statement.executeUpdate(sql1);

            String sql2 = "update tbl_userrealwh set Quantity=Quantity-'"+quantity+"' where UserId = '"+userid+"'and StockId= '"+stockid+"'";
            System.out.println("即将执行的SQL语句是：" + sql2);
            statement.executeUpdate(sql2);

            String sql = "insert into tbl_userwh(UserId,UserName,StockId,StockName,Quantity,BUnitPrice,CreateAt) values('"
                    + userid + "','" + username + "','" + stockid + "','" + stockname + "','" + quantity + "','" + bunitprice + "','" + createTime + "')";
            System.out.println("即将执行的SQL语句是：" + sql);
            statement.executeUpdate(sql);
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
        //changeMoney.reduceMoney(response,userid,money);
        System.out.println("页面执行完毕！");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
