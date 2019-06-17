package news.getNews;

import Broker.MqClient;
import Broker.ProduceClient;
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

/**
 * Created by 11913 on 2019/4/15.
 */
@WebServlet(name = "getNewsAction")
public class getNewsAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*
        //得到token
        String token = tokenExtractor.extractToken(request);
        //从token得到user实例
        User user= tokenChecker.tokenToUser(token);
        //验证
        if(user==null) {
            System.out.println("user==null");
            sendManager.sendSimpleErrorJSON(response);
            return;
        }*/

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;


        //主线程从数据库中获得10条最新新闻返回
        try{
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM tbl_news order by id desc LIMIT 95";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while(rs.next()){
                Map map = new HashMap();
                String title = rs.getString("title");
                map.put("title",title);
                String date = rs.getString("date");
                map.put("date",date);
                String author_name = rs.getString("author_name");
                map.put("author_name",author_name);
                String url = rs.getString("url");
                map.put("url",url);
                String thumbnail_pic_s = rs.getString("thumbnail_pic_s");
                map.put("thumbnail_pic_s",thumbnail_pic_s);
                list.add(map);
            }
            conn.close();
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
