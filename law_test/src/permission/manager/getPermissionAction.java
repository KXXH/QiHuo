package permission.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/25.
 */
public class getPermissionAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String className=request.getParameter("classname");
        int count=0;
        int pageSize=15;
        if(request.getParameter("count")!=null){
            count=Integer.parseInt(request.getParameter("count"));
        }
        if(request.getParameter("pageSize")!=null){
            pageSize=Integer.parseInt(request.getParameter("pageSize"));
        }
        try {
            Connection conn= dbOpener.getDB();
            String sql=null;
            if(className==null||className.length()==0){
                sql="SELECT * FROM tbl_permission LIMIT ? OFFSET ?";
            }else{
                sql="SELECT * FROM tbl_permission WHERE className=? LIMIT ? OFFSET ?";
            }
            PreparedStatement ptmt = conn.prepareStatement(sql);
            if(className==null||className.length()==0){
                ptmt.setInt(1,pageSize);
                ptmt.setInt(2,count);
            }else{
                ptmt.setString(1,className);
                ptmt.setInt(2,pageSize);
                ptmt.setInt(3,count);
            }
            ResultSet rs=ptmt.executeQuery();
            JSONObject jsonObject=new JSONObject();
            JSONArray jsonArray=new JSONArray();
            while(rs.next()){
                JSONObject json=new JSONObject();
                int id=rs.getInt("id");
                String classname=rs.getString("className");
                int code=rs.getInt("permissionCode");
                json.put("id",id);
                json.put("classname",classname);
                json.put("permission_code",code);
                jsonArray.put(json);
                count++;
            }
            if(jsonArray.length()==0){
                jsonObject.put("status","end");
                sendManager.sendJSON(response,jsonObject);
                return;
            }
            conn.close();
            jsonObject.put("data",jsonArray);
            jsonObject.put("status","ok");
            jsonObject.put("count",count);
            sendManager.sendJSON(response,jsonObject);
        } catch (SQLException e) {
            sendManager.sendErrorJSONWithMsg(response,"SQL错误");
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        } catch (JSONException e) {
            sendManager.sendErrorJSONWithMsg(response,"JSON错误");
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
