package warehouse.query;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@WebServlet(name = "querylistAction")
public class querylistAction extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{

        List jsonList = new ArrayList();
        //获取add_file.jsp页面提交后传递过来的参数，在form里的参数才能传递过来，注意name和id的区别

        //String userName = request.getParameter("UserName");
        //String userName=checkTokenName(getCookie(cname));
        String sel1 = request.getParameter("Sel1");
        String sel2 = request.getParameter("Sel2");
        String sel3 = request.getParameter("Sel3");
        //String sel4 = request.getParameter("Sel4");

        String tocken = request.getParameter("Cookie");
        System.out.println(tocken);
        User user = tokenChecker.tokenToUser(tocken);
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
            Connection conn = DriverManager
                    .getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            System.out.println("链接完毕，开始创建准备数据库操作的statement<br>");

            String sql = "select * from tbl_userrealwh WHERE UserId='"+user.getUserId()+"' ORDER BY " +sel1+','+sel2+','+sel3;
            System.out.println("即将执行的SQL语句是：" + sql);


            Statement statement = conn.createStatement();
            System.out.println("Connect Database Ok！！！");
            //执行查询语句，返回结果集
            ResultSet rs = statement.executeQuery(sql);

            System.out.println("执行完毕，逐条显示<br>");
            //如果查询有结果，则循环显示查询出来的记录
            System.out.println("<br>====================开始输出====================");
            while (rs.next()) {

                Map map = new HashMap();

                map.put("stockid",rs.getString("StockId"));
                map.put("stockname",rs.getString("StockName"));
                map.put("quantity",rs.getString("Quantity"));
                map.put("userid",rs.getInt("UserId"));
                map.put("userrealid",user.getUserId());
                //map.put("bunitprice",rs.getString("BUnitPrice"));

                jsonList.add(map);
            }
            //加个断行
            System.out.println("<br>");
            System.out.println("====================显示完毕====================<br>");

            statement.close();
            conn.close();
            System.out.println("Database Closed！！！");
        } catch (SQLException sqlexception) {
            sqlexception.printStackTrace();
        }

        //////////数据库查询完毕，得到了json数组jsonList//////////
        //下面开始构建返回的json
        JSONObject json=new JSONObject();
        try {
            json.put("aaData",jsonList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("result_msg","ok");	//如果发生错误就设置成"error"等
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            json.put("result_code",0);	//返回0表示正常，不等于0就表示有错误产生，错误代码
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("最后构造得到的json是："+json.toString());
        response.setContentType("application/json; charset=UTF-8");
        try {
            response.getWriter().print(json);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("返回结果给调用页面了。");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

