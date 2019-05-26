package news.getNews;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;

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
 * Created by 11913 on 2019/4/17.
 */
@WebServlet(name = "newsSearchAction")
public class newsSearchAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String search = request.getParameter("search");
        try {
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM tbl_news WHERE title LIKE '%"+search+"%'";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while(rs.next()){
                Map map = new HashMap();
                String id = rs.getString("id");
                map.put("id",id);
                String title = rs.getString("title");
                map.put("title",title);
                String date = rs.getString("date");
                map.put("date",date);
                String author_name = rs.getString("author_name");
                map.put("author_name",author_name);
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
