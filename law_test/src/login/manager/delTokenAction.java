package login.manager;

import org.json.JSONArray;
import org.json.JSONException;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.sendManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/23.
 */
public class delTokenAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String[] selects=request.getParameterValues("selects[]");
        if(selects==null){
            sendManager.sendErrorJSONWithMsg(response,"传参为空");
            return;
        }
        try {
            //JSONArray array=new JSONArray(selects);
            Connection conn= dbOpener.getDB();
            String sql="DELETE FROM tbl_tockeninfo WHERE id=?";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            for(int i = 0; selects.length > i; i++){
                int id=Integer.parseInt(selects[i]);
                ptmt.setInt(1,id);
                ptmt.execute();
            }
            conn.close();
            sendManager.sendSimpleOKJSON(response);
        } catch (SQLException e) {
            sendManager.sendErrorJSONWithMsg(response,"SQL执行错误");
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}