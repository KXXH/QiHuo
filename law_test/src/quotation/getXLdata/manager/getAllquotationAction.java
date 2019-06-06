package quotation.getXLdata.manager;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/6/6.
 */
@WebServlet(name = "getAllquotationAction")
public class getAllquotationAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {


        if (!permissionChecker.checkPermissionAndResponse(request, response, this)) return;
        String search="", order_1="id", order_2="Code";
        if (request.getParameter("order_1").equals("id") || request.getParameter("order_1").equals("Code") || request.getParameter("order_1").equals("Name")) {
            order_1 = request.getParameter("order_1");
        }
        if (request.getParameter("order_2").equals("id") || request.getParameter("order_2").equals("Code") || request.getParameter("order_2").equals("Name")) {
            order_2 = request.getParameter("order_2");
        }
        if (request.getParameter("search") != null) {
            search = request.getParameter("search");
        }

        try {
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql;
            if (search == "") {
                sql = "SELECT * FROM tbl_quotation ORDER BY " + order_1 + "," + order_2 + " DESC";
            } else {
                sql = "SELECT * FROM tbl_quotation WHERE Name LIKE '%" + search + "%' ORDER BY " + order_1 + "," + order_2 + " desc ";
            }
            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while (rs.next()) {
                Map map = new HashMap();
                String id = rs.getString("id");
                map.put("id", id);
                String Code = rs.getString("Code");
                map.put("Code", Code);
                String Name = rs.getString("Name");
                map.put("Name", Name);
                Double Quotation = rs.getDouble("Quotation");
                map.put("Quotation", Quotation);
                Double RiseOrFall = rs.getDouble("RiseOrFall");
                map.put("RiseOrFall", RiseOrFall);
                Double ROFper = rs.getDouble("ROFper");
                map.put("ROFper", ROFper);

                list.add(map);
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
