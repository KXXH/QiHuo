package quotation.getXLdata;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.networkOpener;

import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/5/25.
 */
@WebServlet(name = "showUSDCNYupdateAction")
public class showUSDCNYupdateAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        //得到当前数据
        String url = "http://hq.sinajs.cn/list=fx_susdcny";
        String result;
        result = networkOpener.getResponse(url,"GBK").split("\"")[1];
        Map q = new HashMap();
        double now = Double.parseDouble(result.split(",")[2]);
        q.put("now",now);
        double open = Double.parseDouble(result.split(",")[5]);
        q.put("open",open);
        double close = Double.parseDouble(result.split(",")[3]);
        q.put("close",close);
        double low = Double.parseDouble(result.split(",")[7]);
        q.put("low",low);
        double high = Double.parseDouble(result.split(",")[6]);
        q.put("high",high);

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("nowData",q);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        response.setContentType("application/json; charset=UTF-8");
        try {
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("执行完毕已返回");
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
