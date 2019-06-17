package notifications.getFromDB.manager;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.sendManager;

import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/6/17.
 */
@WebServlet(name = "NotificationStatisitcsAction")
public class NotificationStatisitcsAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        try {
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT DATE(tbl_notifications.datetime) AS day, COUNT(tbl_notifications.message) AS message_count FROM tbl_notifications GROUP BY DAY(tbl_notifications.datetime)";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                Map map = new HashMap();
                String day = rs.getString("day");
                map.put("day",day);
                int message_count = rs.getInt("message_count");
                map.put("message_count",message_count);
                list.add(map);
            }
            conn.close();
            jsonObject.put("aaData",list);
            sendManager.sendJSON(response,jsonObject);
        } catch (SQLException e) {
            e.printStackTrace();
            sendManager.sendSimpleErrorJSON(response);
        } catch (JSONException e) {
            e.printStackTrace();
            sendManager.sendSimpleErrorJSON(response);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
