package news.getNews;

import Broker.ProduceClient;
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
@WebServlet(name = "newsDeleteAction")
public class newsDeleteAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String delete_id = request.getParameter("id");
        System.out.println(delete_id);
        JSONObject jsonObject = new JSONObject();
        try {
            Connection conn = dbOpener.getDB();
            String sql = "DELETE FROM tbl_news WHERE id=?;";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, delete_id);
            ptmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            try {
                jsonObject.put("status",0);
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
