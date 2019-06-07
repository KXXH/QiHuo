package login.manager;

import permission.manager.permissionChecker;
import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/6/7.
 */
public class editLoginRecordAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String id=request.getParameter("id");
        String username=request.getParameter("username");
        String time=request.getParameter("time");
        String action=request.getParameter("action");
        String ip=request.getParameter("ip");
        String location=request.getParameter("location");
        try {
            Connection conn= dbOpener.getDB();
            String sql="UPDATE tbl_logininfo SET action=?,ip=?,location=? WHERE id=?";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setString(1,action);
            ptmt.setString(2,ip);
            ptmt.setString(3,location);
            ptmt.setInt(4,Integer.parseInt(id));
            ptmt.execute();
            sql="SELECT * FROM tbl_logininfo WHERE id=?";
            ptmt=conn.prepareStatement(sql);
            ptmt.setInt(1,Integer.parseInt(id));
            ResultSet rs=ptmt.executeQuery();
            while(rs.next()){
                System.out.println("editLoginRecord:"+rs.getString("action"));
            }
            sendManager.sendSimpleOKJSON(response);
            System.out.println("更新已完成!");
        } catch (SQLException e) {
            exceptionManager.logException(e,this, tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            sendManager.sendErrorJSONWithMsg(response,"SQL错误");
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
