package notifications.getFromDB.manager;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.sendManager;

import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/6/17.
 */
@WebServlet(name = "NotificationDeleteAction")
public class NotificationDeleteAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if (!permissionChecker.checkPermissionAndResponse(request, response, this)) return;

        String delete_id = request.getParameter("id");
        System.out.println(delete_id);
        try {
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "DELETE FROM tbl_notifications WHERE id=?";
            System.out.println(sql);
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, delete_id);
            ptmt.execute();
            conn.close();
            sendManager.sendSimpleOKJSON(response);
        } catch (SQLException e) {
            sendManager.sendSimpleErrorJSON(response);
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
