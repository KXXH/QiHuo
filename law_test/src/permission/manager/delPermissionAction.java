package permission.manager;

import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/25.
 */
public class delPermissionAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        try{
            int id=Integer.parseInt(request.getParameter("id"));
            Connection conn= dbOpener.getDB();
            String sql="DELETE FROM tbl_permission WHERE id=?";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setInt(1,id);
            ptmt.execute();
            sendManager.sendSimpleOKJSON(response);
            conn.close();
        }catch(NullPointerException e){
            sendManager.sendErrorJSONWithMsg(response,"传参错误");

            exceptionManager.logException(e,this, tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        } catch (SQLException e) {
            sendManager.sendErrorJSONWithMsg(response,"SQL错误");
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
