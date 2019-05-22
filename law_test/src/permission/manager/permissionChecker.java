package permission.manager;

import user.manager.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

import utils.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zjm97 on 2019/5/22.
 */
public class permissionChecker {
    public static boolean checkPermission(javax.servlet.http.HttpServlet servlet,User user) throws SQLException {
        String classname=servlet.getClass().getCanonicalName();
        System.out.println("classname="+classname);
        Connection connection=dbOpener.getDB();
        String sql="SELECT * FROM tbl_permission WHERE className=?";
        PreparedStatement ptmt=connection.prepareStatement(sql);
        ptmt.setString(1,classname);
        ResultSet rs=ptmt.executeQuery();
        if(rs.next()){
            int code = rs.getInt("permissionCode");
            System.out.println("code="+code);
            if((code&1)==1){
                return true;
            }else if((code&2)==2&& Objects.equals(user.getRole_id(), "unchecked")){
                return  true;
            }else if((code&4)==4&& Objects.equals(user.getRole_id(), "normal")){
                return true;
            }else if((code&8)==8&& Objects.equals(user.getRole_id(), "admin")){
                return true;
            }else if((code&16)==16&& Objects.equals(user.getRole_id(), "super_admin")){
                return true;
            } else{
                return false;
            }
        }
        return false;
    }
    public static boolean checkPermissionAndResponse(HttpServletRequest request, HttpServletResponse response, HttpServlet servlet){
        String token=tokenExtractor.extractToken(request);
        User user=tokenChecker.tokenToUser(token);
        try {
            boolean flag=checkPermission(servlet,user);
            if(flag){
                return true;
            }else{
                sendManager.sendSimpleErrorJSON(response);
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        sendManager.sendSimpleErrorJSON(response);
        return false;
    }
}
