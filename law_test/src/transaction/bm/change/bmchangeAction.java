package transaction.bm.change;

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
        String createat = request.getParameter("CreateAt");
        String a=null;
        JSONObject jsonObject = new JSONObject();
        System.out.println(orderid+userid+user_name+stockid+stockname+quantity+bunitprice+createat);
        try {
            Connection conn = dbOpener.getDB();
            //String sql = "UPDATE tbl_news SET title=?, date=?, author_name=? WHERE id=?";
            System.out.println("!!!!!!!!!"+userid);
            System.out.println("?????????"+user.getUserId());
            if(userid==user.getUserId())
            {
                System.out.println("到这里了!!!!!!!!!");
                //sendManager.sendErrorJSONWithMsgAndCode(response,a,2);
                return;
            }
            String sql = "update tbl_userwh set UserId='" + userid + "',UserName='" + user_name + "',StockId='" + stockid + "',StockName='" + stockname + "',Quantity='" + quantity + "',BUnitPrice='" + bunitprice + "',CreateAt='" + createat + "' WHERE OrderId='" + orderid + "'";
            System.out.println(sql);
            Statement statement = conn.createStatement();
            //PreparedStatement ptmt = conn.prepareStatement(sql);
            //ptmt = conn.prepareStatement(sql);
            /*ptmt.setString(1, title);
            ptmt.setString(2, date);
            ptmt.setString(3, author_name);
            ptmt.setString(4, id);
            ptmt.execute();*/
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
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
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

