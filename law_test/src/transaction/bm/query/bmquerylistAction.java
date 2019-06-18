package transaction.bm.query;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.dbOpener;
import utils.sendManager;
import utils.tokenChecker;


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


@WebServlet(name = "bmquerylistAction")
public class bmquerylistAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String sel1 = request.getParameter("Sel1");
        String sel2 = request.getParameter("Sel2");
        String sel3 = request.getParameter("Sel3");
        String sel4 = request.getParameter("Sel4");

        String orderid = request.getParameter("OrderId");
        String userid = request.getParameter("UserId");
        String username = request.getParameter("UserName");
        String stockid = request.getParameter("StockId");
        String stockname = request.getParameter("StockName");

        int len = Integer.parseInt(request.getParameter("length"));
        int count = 0;
        int limit = 0;
        String search="";
        if(request.getParameter("limit")!=null){
            limit=Integer.parseInt(request.getParameter("limit"));
        }
        try{
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql;
            if(orderid.length()==0 && userid.length()==0 && username.length()==0 && stockid.length()==0 && stockname.length()==0){
                sql = "SELECT * FROM tbl_bm ORDER BY " + sel1 +"," +sel1 + "," +sel3 + "," +sel4 + " desc LIMIT " +limit;
                System.out.println("执行的SQL语句是:"+sql);
            }
            else{
                sql = "select * from tbl_bm WHERE UserId='"+userid+"'or UserName='"+username+"' or StockId='"+stockid+"' or StockName='"+stockname+"' or OrderId='"+orderid+"' ORDER BY "+sel1+","+sel2+","+sel3+","+sel4+" DESC LIMIT " +limit;
                System.out.println("执行的SQL语句是:"+sql);
            }
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while(rs.next()){
                Map map = new HashMap();
                map.put("orderid",rs.getString("OrderId"));
                map.put("userid",rs.getString("UserId"));
                map.put("username",rs.getString("UserName"));
                map.put("stockid",rs.getString("StockId"));
                map.put("stockname",rs.getString("StockName"));
                map.put("quantity",rs.getString("Quantity"));
                map.put("bunitprice",rs.getString("BUnitPrice"));
                map.put("createat",rs.getString("CreateAt"));
                list.add(map);
                count++;
            }
            conn.close();
            if(count == len){
                jsonObject.put("end",1);
            }
            jsonObject.put("list",list);
            System.out.println(jsonObject.getString("list"));
            response.setContentType("application/json; charset=UTF-8");
            try {
                response.getWriter().print(jsonObject);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("执行完毕已返回");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}


