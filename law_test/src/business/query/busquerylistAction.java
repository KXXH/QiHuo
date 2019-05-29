package business.query;

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


/*@WebServlet(name = "busquerylistAction")
public class busquerylistAction extends HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        List jsonList = new ArrayList();
        //获取add_file.jsp页面提交后传递过来的参数，在form里的参数才能传递过来，注意name和id的区别

        //String userName = request.getParameter("UserName");
        //String userName=checkTokenName(getCookie(cname));
        String sel1 = request.getParameter("Sel1");
        String sel2 = request.getParameter("Sel2");
        String sel3 = request.getParameter("Sel3");
        String sel4 = request.getParameter("Sel4");

        String orderid = request.getParameter("OrderId");
        String userid = request.getParameter("UserId");
        String username = request.getParameter("UserName");
        String stockid = request.getParameter("StockId");
        String stockname = request.getParameter("StockName");



        int count=1111111111;
        int page_size=10;
        int rowCount=0;
        if(request.getParameter("page_size")!=null){
            page_size=Integer.parseInt(request.getParameter("page_size"));
        }
        System.out.println("page_size="+page_size);
        //String s=String.valueOf(user.getUserId());
        //链接数据库，加载jdbc的驱动com.mysql.jdbc.Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("加载了驱动<br>");
        } catch (ClassNotFoundException classnotfoundexception) {
            //如果有异常就抛出
            classnotfoundexception.printStackTrace();
        }
        try {
            System.out.println("开始链接数据库<br>");
            //链接数据库，IP地址是localhost，端口是3306，账号和密码是ylx，这些都可以更改
            Connection conn = dbOpener.getDB();
            System.out.println("链接完毕，开始创建准备数据库操作的statement<br>");
            String sql;
            if(orderid.length()==0 && userid.length()==0 && username.length()==0 && stockid.length()==0 && stockname.length()==0)
            {
                sql = "select * from tbl_userwh ORDER BY '"+sel1+"','"+sel2+"','"+sel3+"','"+sel4+"' DESC LIMIT ? ";
            }
            else
            {
                sql = "select * from tbl_userwh WHERE UserId='"+userid+"' or UserName='"+username+"' or StockId='"+stockid+"' or StockName='"+stockname+"' or OrderId='"+orderid+"' ORDER BY '"+sel1+"','"+sel2+"','"+sel3+"','"+sel4+"' DESC LIMIT ? ";
            }
            System.out.println("即将执行的SQL语句是：" + sql);

            System.out.println("userid="+userid);
            System.out.println("username="+username);
            System.out.println("stockid="+stockid);
            System.out.println("stockname="+stockname);
            System.out.println("orderid="+orderid);
            System.out.println("page_size="+page_size);
            System.out.println("count="+count);
            System.out.println("Connect Database Ok！！！");
            //执行查询语句，返回结果集
            PreparedStatement ps = conn.prepareStatement(sql);    //实例化PreparedStatement对象

            ps.setInt(1,page_size);
            ResultSet rs = ps.executeQuery();   //执行预处理语句获取查询结果集

            System.out.println("执行完毕，逐条显示<br>");
            //如果查询有结果，则循环显示查询出来的记录
            System.out.println("<br>====================开始输出====================");
            while (rs.next()) {

                JSONObject json = new JSONObject();

                json.put("orderid",rs.getString("OrderId"));
                json.put("userid",rs.getString("UserId"));
                json.put("username",rs.getString("UserName"));
                json.put("stockid",rs.getString("StockId"));
                json.put("stockname",rs.getString("StockName"));
                json.put("quantity",rs.getString("Quantity"));
                json.put("bunitprice",rs.getString("BUnitPrice"));
                json.put("createat",rs.getString("CreateAt"));
                jsonList.add(json);

                System.out.println("行数="+rs.getRow());
                if(rs.getRow()>rowCount)rowCount=rs.getRow();
            }
            //加个断行
            System.out.println("<br>");
            System.out.println("====================显示完毕====================<br>");

            ps.close();
            conn.close();
            System.out.println("Database Closed！！！");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        //////////数据库查询完毕，得到了json数组jsonList//////////
        //下面开始构建返回的json
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("list",jsonList);
            if(page_size==rowCount){
                JSONObject j=new JSONObject();
                j.put("status","end");
                sendManager.sendJSON(response,j);
                return;
            }
            jsonObject.put("status","ok");	//如果发生错误就设置成"error"等
            jsonObject.put("result_code",0);	//返回0表示正常，不等于0就表示有错误产生，错误代码
            jsonObject.put("page_size",page_size);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("最后构造得到的json是："+jsonObject.toString());
        response.setContentType("application/json; charset=UTF-8");
        try {
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("返回结果给调用页面了。");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}*/

@WebServlet(name = "busquerylistAction")
public class busquerylistAction extends HttpServlet {
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
                sql = "SELECT * FROM tbl_userwh ORDER BY " + sel1 +"," +sel1 + "," +sel3 + "," +sel4 + " desc LIMIT " +limit;
            }
            else{
                sql = "select * from tbl_userwh WHERE UserId='"+userid+"'or UserName='"+username+"' or StockId='"+stockid+"' or StockName='"+stockname+"' or OrderId='"+orderid+"' ORDER BY "+sel1+","+sel2+","+sel3+","+sel4+" DESC LIMIT " +limit;
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


