package permission.manager;

import utils.dbOpener;
import utils.sendManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/25.
 */
public class editPermissionAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        try{
            int id=Integer.parseInt(request.getParameter("id"));
            int code=Integer.parseInt(request.getParameter("permission_code"));
            String classname=request.getParameter("classname");
            Connection conn= dbOpener.getDB();
            String sql="UPDATE tbl_permission SET permissionCode=?,className=? WHERE id=?";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setInt(1,code);
            ptmt.setString(2,classname);
            ptmt.setInt(3,id);
            ptmt.execute();
            sendManager.sendSimpleOKJSON(response);
        }catch(NullPointerException e){
            sendManager.sendErrorJSONWithMsg(response,"传参错误");
            e.printStackTrace();
        } catch (SQLException e) {
            sendManager.sendErrorJSONWithMsg(response,"SQL错误");
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
