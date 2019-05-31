package news.getNews;

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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by 11913 on 2019/4/17.
 */
@WebServlet(name = "newsManagerAction")
public class newsManagerAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /*
        //得到token
        String token = tokenExtractor.extractToken(request);
        //从token得到user实例
        User user= tokenChecker.tokenToUser(token);
        //验证
        if(user==null|| !Objects.equals(user.getRole_id(), "admin")) {
                sendManager.sendSimpleErrorJSON(response);
                return;
        }
        */

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String order_1 = request.getParameter("order_1");
        String order_2 = request.getParameter("order_2");
        int len = Integer.parseInt(request.getParameter("length"));
        int count = 0;
        int limit = 0;
        String search="";
        if(request.getParameter("limit")!=null){
            limit=Integer.parseInt(request.getParameter("limit"));
        }
        if(request.getParameter("search")!=null){
            search = request.getParameter("search");
        }
        int page_size=15;
        try{
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql;
            if(search==""){
                sql = "SELECT * FROM tbl_news ORDER BY " + order_1 +"," +order_2 + " desc LIMIT " +limit;
            }
            else{
                sql = "SELECT * FROM tbl_news WHERE title LIKE '%"+search+"%' ORDER BY " + order_1 +"," +order_2 + " desc LIMIT " +limit;
            }
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while(rs.next()){
                Map map = new HashMap();
                String id = rs.getString("id");
                map.put("id",id);
                String url = rs.getString("url");
                map.put("url",url);
                String title = rs.getString("title");
                map.put("title",title);
                String date = rs.getString("date");
                map.put("date",date);
                String author_name = rs.getString("author_name");
                map.put("author_name",author_name);
                list.add(map);
                count++;
            }
            conn.close();
            if(count == len){
                jsonObject.put("end",1);
            }
            jsonObject.put("aaData",list);
            System.out.println(jsonObject.getString("aaData"));
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
