package permission.manager;

import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/5/25.
 */
public class addPermissionAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        try{
            String classname=request.getParameter("classname");
            int code=Integer.parseInt(request.getParameter("permission_code"));
            Connection conn= dbOpener.getDB();
            String sql="INSERT INTO tbl_permission (className,permissionCode) VALUES(?,?)";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setInt(2,code);
            ptmt.setString(1,classname);
            ptmt.execute();
            sendManager.sendSimpleOKJSON(response);
            conn.close();
        }catch(NullPointerException e){
            sendManager.sendErrorJSONWithMsg(response,"传参错误");
            exceptionManager.logException(e,this, tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        } catch (SQLException e) {
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            sendManager.sendErrorJSONWithMsg(response,"SQL错误");
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
