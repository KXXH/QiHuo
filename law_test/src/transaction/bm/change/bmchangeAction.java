package transaction.bm.change;

import Broker.ProduceClient;
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
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet(name = "bmchangeAction")
public class bmchangeAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String tocken= tokenExtractor.extractToken(request);
        System.out.println(tocken);
        User user = tokenChecker.tokenToUser(tocken);

        int orderid = Integer.parseInt(request.getParameter("OrderId"));
        int userid =Integer.parseInt(request.getParameter("UserId"));
        String user_name = request.getParameter("UserName");
        int stockid = Integer.parseInt(request.getParameter("StockId"));
        String stockname = request.getParameter("StockName");
        int quantity = Integer.parseInt(request.getParameter("Quantity"));
        double bunitprice = Double.valueOf(request.getParameter("BUnitPrice"));

        java.util.Date currDate = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createat = sdf.format(currDate);

        String a=null;
        String ystockid=null;
        String ystockname=null;
        Connection conn = null;


        JSONObject jsonObject = new JSONObject();
        System.out.println(orderid+userid+user_name+stockid+stockname+quantity+bunitprice+createat);
        if(userid==user.getUserId())
        {
            System.out.println("到这里了!!!!!!!!!");
            sendManager.sendErrorJSONWithMsgAndCode(response,a,2);
            return;
        }
        else if((quantity*bunitprice)>user.getBalance())  ///user.getBalance()
        {
            System.out.println("到这里了2222222");
            sendManager.sendErrorJSONWithMsgAndCode(response,a,3);
            return;
        }
        else {
            try {
                    conn = dbOpener.getDB();
                    conn.setAutoCommit(false);
                    //String sql = "UPDATE tbl_news SET title=?, date=?, author_name=? WHERE id=?";
                    System.out.println("!!!!!!!!!" + userid);
                    System.out.println("?????????" + user.getUserId());
                    String sql6 = "select * from tbl_bm where OrderId=?";
                    PreparedStatement ps2;
                    ps2 = conn.prepareStatement(sql6);    //实例化PreparedStatement对象
                    ps2.setInt(1, orderid);
                    ResultSet rs2 = ps2.executeQuery();   //执行预处理语句获取查询结果集
                    int checkorderid=0;
                    if(!(rs2.next()))
                    {
                        System.out.println("到这里了11111111");
                        sendManager.sendErrorJSONWithMsgAndCode(response,a,5);
                        return;
                    }

                    String sql5 = "select * from tbl_bm WHERE UserId=? and StockId=? and StockName=?";
                    PreparedStatement ps1;
                    ps1 = conn.prepareStatement(sql5);    //实例化PreparedStatement对象
                    ps1.setInt(1, userid);
                    ps1.setInt(2, stockid);       //设置预处理语句参数
                    ps1.setString(3, stockname);
                    ResultSet rs1 = ps1.executeQuery();   //执行预处理语句获取查询结果集
                    int checkquantity=0;
                    System.out.println("执行完毕，逐条显示<br>");

                    //如果查询有结果，则循环显示查询出来的记录
                    while (rs1.next()) {

                        checkquantity += rs1.getInt("Quantity");

                    }
                    if(quantity>checkquantity)
                    {
                        System.out.println("到这里了33333333");
                        sendManager.sendErrorJSONWithMsgAndCode(response,a,4);
                        return;
                    }


                    String sql1 = "select * from tbl_userrealwh WHERE UserId=? and StockId=? and StockName=?";  //查一下有没有已经存在了吗
                    //String sql3 = "select * from tbl_userrealwh WHERE UserName=? and StockId=? and StockName=?";
                    System.out.println("即将执行的SQL1语句是：" + sql1);

                    System.out.println(stockid);
                    PreparedStatement ps;
                    ps = conn.prepareStatement(sql1);    //实例化PreparedStatement对象
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
                    String sql2;
                    if (ystockid != null && ystockname != null) {
                        sql2 = "update tbl_userrealwh set Quantity='" + quantityint
                                + "',BUnitPrice='"+bunitprice+"',CreateAt='" + createat + "' WHERE UserId='" + user.getUserId() + "' and StockId='" + stockid + "'";
                        System.out.println("即将执行的SQL2语句是：" + sql2);
                    } else {
                        sql2 = "insert into tbl_userrealwh(UserId,UserName,StockId,StockName,Quantity,BUnitPrice,CreateAt) values('"
                                + user.getUserId() + "','" + user.getUserName() + "','" + stockid + "','" + stockname + "','" + quantityint + "','"+bunitprice+"','" + createat + "')";
                        System.out.println("即将执行的SQL2语句是：" + sql2);
                    }
                    System.out.println(sql2);
                    Statement statement = conn.createStatement();
                    statement.executeUpdate(sql2);

                    String sql3 = "insert into tbl_userwh(UserId,UserName,StockId,StockName,Quantity,BUnitPrice,CreateAt) values('"
                            + user.getUserId() + "','" + user.getUserName() + "','" + stockid + "','" + stockname + "','" + quantity + "','" + bunitprice + "','" + createat + "')";
                    System.out.println("即将执行的SQL3语句是：" + sql3);
                    statement.executeUpdate(sql3);

                    String sql4 = "delete from tbl_bm where OrderId=" + orderid;
                    System.out.println("即将执行的SQL4语句是：" + sql4);
                    statement.executeUpdate(sql4);
                    double money = quantity*bunitprice;

                    String sql7 = "update tbl_userinfo set Balance=Balance-'"+money+"' where UserId = '"+user.getUserId()+"'";
                    System.out.println("即将执行的SQL语句是：" + sql7);
                    statement.executeUpdate(sql7);
                    //changeMoney.reduceMoney(response,user.getUserId(),money);
                    String sql8 = "update tbl_userinfo set Balance=Balance+'"+money+"' where UserId = '"+userid+"'";
                    System.out.println("即将执行的SQL语句是：" + sql8);
                    statement.executeUpdate(sql8);
                    //changeMoney.increaseMoney(response,userid,money);
                    conn.commit();//提交事务
                    statement.close();
                    conn.close();

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
                System.out.println("操作数据完毕，关闭了数据库！");

                ProduceClient produceClient = new ProduceClient();
                try {
                    int saler_id = userid;
                    User saler = User.findUserById(saler_id);
                    String saler_name = saler.getUserName();
                    User buyer = user;
                    String buyer_name = user.getUserName();
                    String message = saler_name + ":您的订单已被"+buyer_name+"购买！";
                    produceClient.send(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                try {
                    conn.rollback();//某一条数据添加失败时，回滚
                    System.out.println("进行了回滚!!!!!!!!!!!!!!!!!");
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                try {
                    jsonObject.put("status", "error");
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

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

