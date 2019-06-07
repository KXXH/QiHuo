package login.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.*;

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
        String username=request.getParameter("username");
        String sort_by_1=request.getParameter("sort_by_1");
        String sort_by_2=request.getParameter("sort_by_2");
        try {
            System.out.println("执行了这个！！");
            Connection connection= dbOpener.getDB();
            String sql=null;
            if(username==null||username.length()==0)
                sql="SELECT * FROM tbl_tockeninfo";
            else
                sql="SELECT * FROM tbl_tockeninfo WHERE UserName=?";

            switch(sort_by_1){
                case "id":case "UserName":case "UserId":case "TTL":break;
                default:sendManager.sendErrorJSONWithMsg(response,"错误:传入的参数非法!");return;
            }
            switch(sort_by_2){
                case "id":case "UserName":case "UserId":case "TTL":break;
                default:sendManager.sendErrorJSONWithMsg(response,"错误:传入的参数非法!");return;
            }
            sql=sql+" ORDER BY "+sort_by_1+","+sort_by_2;
            PreparedStatement ptmt=connection.prepareStatement(sql);
            if(!(username==null||username.length()==0))
                ptmt.setString(1,username);
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
                    exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                    e.printStackTrace();
                }
            }
            json.put("data",jsonArray);
            json.put("status","ok");
            sendManager.sendJSON(response,json);
            connection.close();
        } catch (SQLException | JSONException e) {
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
