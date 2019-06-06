package quotation.getXLdata.manager;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.sendManager;

import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/6/6.
 */
@WebServlet(name = "QuotationStatisitcsAction")
public class QuotationStatisitcsAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        try {
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT MONTH(tbl_usdcny.date) AS month, AVG(tbl_usdcny.close) AS avg_close, AVG(tbl_usdcny.open) AS avg_open, AVG(tbl_usdcny.high) AS avg_high, AVG(tbl_usdcny.low) AS avg_low FROM tbl_usdcny GROUP BY MONTH(tbl_usdcny.date)";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                Map map = new HashMap();
                String month = rs.getString("month");
                map.put("month","2019-"+ month);
                Double avg_close = rs.getDouble("avg_close");
                map.put("avg_close", new DecimalFormat("#.0000").format(avg_close));
                Double avg_open = rs.getDouble("avg_open");
                map.put("avg_open", new DecimalFormat("#.0000").format(avg_open));
                Double avg_high = rs.getDouble("avg_high");
                map.put("avg_high", new DecimalFormat("#.0000").format(avg_high));
                Double avg_low = rs.getDouble("avg_low");
                map.put("avg_low", new DecimalFormat("#.0000").format(avg_low));
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
