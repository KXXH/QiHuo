package login.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.sendManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/23.
 */
public class getTokenAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        try {
            System.out.println("执行了这个！！");
            Connection connection= dbOpener.getDB();
            String sql="SELECT * FROM tbl_tockeninfo";
            PreparedStatement ptmt=connection.prepareStatement(sql);
            ResultSet rs=ptmt.executeQuery();
            JSONObject json=new JSONObject();
            JSONArray jsonArray=new JSONArray();
            while(rs.next()){
                JSONObject jsonObject=new JSONObject();
                try {
                    jsonObject.put("id",rs.getString("id"));
                    jsonObject.put("username",rs.getString("UserName"));
                    jsonObject.put("TTL",rs.getString("TTL"));
                    jsonObject.put("tokenValue",rs.getString("tockenValue"));
                    jsonArray.put(jsonObject);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            json.put("data",jsonArray);
            json.put("status","ok");
            sendManager.sendJSON(response,json);
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
