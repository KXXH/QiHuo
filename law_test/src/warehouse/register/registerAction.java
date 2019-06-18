package warehouse.register;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.*;

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
import java.util.HashMap;
import java.util.Map;


@WebServlet(name = "registerAction")
public class registerAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        //获取add_file.jsp页面提交后传递过来的参数，在form里的参数才能传递过来，注意name和id的区别


        int stockid = Integer.parseInt(request.getParameter("StockId"));
        String stockname = request.getParameter("StockName");
        int quantity = Integer.parseInt(request.getParameter("Quantity"));
        double bunitprice = Double.valueOf(request.getParameter("BUnitPrice"));
        String createat = request.getParameter("CreateAt");

        String ystockid=null;
        String ystockname=null;
        Connection conn = null;


        String tocken= tokenExtractor.extractToken(request);
        User user = tokenChecker.tokenToUser(tocken);
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
            conn = dbOpener.getDB();
            conn.setAutoCommit(false);
            System.out.println("准备statement。");
            Statement statement = conn.createStatement();
            System.out.println("已经链接上数据库！");

            String sql4 = "select * from tbl_userrealwh WHERE UserId=? and StockId=? and StockName=?";  //查一下有没有已经存在了吗
            //String sql3 = "select * from tbl_userrealwh WHERE UserName=? and StockId=? and StockName=?";
            System.out.println("即将执行的SQL4语句是：" + sql4);

            System.out.println(stockid);
            PreparedStatement ps;
            ps = conn.prepareStatement(sql4);    //实例化PreparedStatement对象
            ps.setInt(1, user.getUserId());
            ps.setInt(2, stockid);       //设置预处理语句参数
            ps.setString(3, stockname);
            ResultSet rs = ps.executeQuery();   //执行预处理语句获取查询结果集
            System.out.println("执行完毕，逐条显示<br>");
            int quantityint = quantity;
            //如果查询有结果，则循环显示查询出来的记录
            while (rs.next()) {

                double b = rs.getDouble("Quantity");
                quantityint += b;
                ystockid = rs.getString("StockId");
                ystockname = rs.getString("StockName");
            }
            System.out.println(quantityint);
            System.out.println(stockid);
            System.out.println(stockname);
            System.out.println(ystockid);
            System.out.println(ystockname);
            String sql3;
            if (ystockid != null && ystockname != null) {
                sql3 = "update tbl_userrealwh set Quantity='" + quantityint
                        + "',BUnitPrice='"+bunitprice+"',CreateAt='" + createat + "' WHERE UserId='" + user.getUserId() + "' and StockId='" + stockid + "'";
                System.out.println("即将执行的SQL3语句是：" + sql3);
            } else {
                sql3 = "insert into tbl_userrealwh(UserId,UserName,StockId,StockName,Quantity,BUnitPrice,CreateAt) values('"
                        + user.getUserId() + "','" + user.getUserName() + "','" + stockid + "','" + stockname + "','" + quantityint + "','"+bunitprice+"','" + createat + "')";
                System.out.println("即将执行的SQL2语句是：" + sql3);
            }
            System.out.println(sql3);
            statement = conn.createStatement();
            statement.executeUpdate(sql3);

            String sql1 = "insert into tbl_bm(UserId,UserName,StockId,StockName,Quantity,BUnitPrice,CreateAt) values('"
                    + user.getUserId() + "','" + user.getUserName() + "','" + stockid + "','" + stockname + "','" + quantity + "','" + bunitprice + "','" + createat + "')";
            System.out.println("即将执行的SQL1语句是：" + sql1);
            statement.executeUpdate(sql1);

            String sql = "insert into tbl_userwh(UserId,UserName,StockId,StockName,Quantity,BUnitPrice,CreateAt) values('"
                    + user.getUserId() + "','" + user.getUserName() + "','"+ stockid + "','" + stockname + "','" + quantity + "','" + bunitprice + "','" + createat + "')";
            System.out.println("即将执行的SQL语句是：" + sql);
            statement.executeUpdate(sql);
            double money = quantity*bunitprice;

            String sql2 = "update tbl_userinfo set Balance=Balance-'"+money+"' where UserId = '"+user.getUserId()+"'";
            System.out.println("即将执行的SQL语句是：" + sql2);
            statement.executeUpdate(sql2);
            //changeMoney.reduceMoney(response,user.getUserId(),money);
            conn.commit();//提交事务
            statement.close();
            conn.close();
            System.out.println("操作数据完毕，关闭了数据库！");
            } catch (SQLException e) {
            try {
                conn.rollback();//某一条数据添加失败时，回滚
                System.out.println("进行了回滚!!!!!!!!!!!!!!!!!");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            try {
                jsonObject.put("status", "0");
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
        System.out.println("页面执行完毕！");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
