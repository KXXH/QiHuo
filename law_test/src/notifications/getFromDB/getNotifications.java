package notifications.getFromDB;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.dbOpener;
import utils.sendManager;
import utils.tokenChecker;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/6/7.
 */
@WebServlet(name = "getNotifications")
public class getNotifications extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String token = (String)request.getSession().getAttribute("token");
        User user = tokenChecker.tokenToUser(token);
        String username = user.getUserName();
        System.out.println(username);
        try {
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM tbl_notifications";
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            int num = 1;
            while (rs.next()) {
                String message = rs.getString("message");
                //System.out.println(message.split(":")[0]);
                if(message.split(":")[0].equals(username)){
                    Map map = new HashMap();
                    int id = rs.getInt("id");
                    map.put("id",id);
                    map.put("num", num);
                    num++;
                    map.put("message",message.split(":")[1]);
                    String datetime = rs.getString("datetime");
                    map.put("datetime",datetime);

                    list.add(map);
                }
            }
            conn.close();
            jsonObject.put("aaData",list);
            sendManager.sendJSON(response,jsonObject);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
