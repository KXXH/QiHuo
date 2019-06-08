package index.query;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.dbOpener;
import utils.sendManager;
import utils.tokenChecker;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet(name = "indexquerylistAction")
public class indexquerylistAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

java.util.Date currDate = Calendar.getInstance().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createTime = sdf.format(currDate);
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String a = null;
        try{
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql;

            sql = "SELECT * FROM tbl_quotation";

            System.out.println(sql);
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            while(rs.next()){
                Map map = new HashMap();
                map.put("id",rs.getString("id"));
                map.put("name",rs.getString("Name"));
                map.put("quotation",rs.getString("Quotation"));
                map.put("riseorfall",rs.getString("RiseOrFall"));
                map.put("rofper",rs.getString("ROFper"));
                map.put("createat",createTime);
                list.add(map);
            }
            conn.close();

            jsonObject.put("list",list);
            System.out.println(jsonObject.getString("list"));
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
            sendManager.sendErrorJSONWithMsgAndCode(response,a,0);
        } catch (JSONException e) {
            e.printStackTrace();
            sendManager.sendErrorJSONWithMsgAndCode(response,a,0);
        }
        System.out.println("执行完毕已返回");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}


