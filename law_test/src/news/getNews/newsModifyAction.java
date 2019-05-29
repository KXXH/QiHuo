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
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 11913 on 2019/4/17.
 */
@WebServlet(name = "newsModifyAction")
public class newsModifyAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String id = request.getParameter("id");
        String title = request.getParameter("title");
        String date = request.getParameter("date");
        String author_name = request.getParameter("author_name");
        JSONObject jsonObject = new JSONObject();
        try {
            Connection conn = dbOpener.getDB();
            String sql = "UPDATE tbl_news SET title=?, date=?, author_name=? WHERE id=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, title);
            ptmt.setString(2, date);
            ptmt.setString(3, author_name);
            ptmt.setString(4, id);
            ptmt.execute();
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
