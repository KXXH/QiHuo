package notifications.getFromDB.manager;

import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.sendManager;

import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 11913 on 2019/6/17.
 */
@WebServlet(name = "NotificationModifyAction")
public class NotificationModifyAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if (!permissionChecker.checkPermissionAndResponse(request, response, this)) return;

        String id = request.getParameter("id");
        String message = request.getParameter("message");
        String datetime = request.getParameter("datetime");

        try {
            Connection conn = dbOpener.getDB();
            String sql = "UPDATE tbl_notifications SET message=? WHERE id=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, message);
            ptmt.setString(2, id);
            ptmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            sendManager.sendSimpleErrorJSON(response);
        }
        sendManager.sendSimpleOKJSON(response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
