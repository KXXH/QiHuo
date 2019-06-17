package quotation.getXLdata;

import Broker.ProduceClient;
import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/5/23.
 */
@WebServlet(name = "getDataToShowAction")
public class getDataToShowAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        try{
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM tbl_quotation order by id";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while(rs.next()){
                Map map = new HashMap();
                String code = rs.getString("Code");
                map.put("code",code);
                String name = rs.getString("Name");
                map.put("name",name);
                String image = rs.getString("image");
                map.put("image",image);
                double quotation = rs.getDouble("Quotation");
                map.put("quotation",quotation);
                double riseorfall = rs.getDouble("RiseOrFall");
                map.put("riseorfall",riseorfall);
                double ROFper = rs.getDouble("ROFper");
                map.put("ROFper",ROFper);
                int kind = rs.getInt("kind");
                map.put("kind",kind);

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

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
