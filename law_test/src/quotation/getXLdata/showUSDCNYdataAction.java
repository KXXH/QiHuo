package quotation.getXLdata;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.networkOpener;

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
 * Created by 11913 on 2019/5/25.
 */
@WebServlet(name = "showUSDCNYdataAction")
public class showUSDCNYdataAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        //得到K线图
        try{
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM tbl_usdcny order by id LIMIT 90";
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while(rs.next()){
                Map map = new HashMap();
                String date = rs.getString("date");
                map.put("date",date);
                double open = rs.getDouble("open");
                map.put("open",open);
                double high = rs.getDouble("high");
                map.put("high",high);
                double low = rs.getDouble("low");
                map.put("low",low);
                double close = rs.getDouble("close");
                map.put("close",close);

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
