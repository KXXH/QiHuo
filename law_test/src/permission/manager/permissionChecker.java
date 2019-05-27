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

/**
 * 权限检查功能，在servlet中应用权限管理只需要在开头加上下面一行代码即可。
 * if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
 * 权限管理器会自动访问数据库检查该servlet对应的权限和用户权限是否匹配，如不匹配还会自动发送错误信息。
 * 权限码各位含义：
 * |位数 |    含义       |
 * | 1  |未登录用户      |
 * | 2  |unchecked用户  |
 * | 3  |normal用户     |
 * | 4  |admin管理员     |
 * | 5  |super_admin超管 |
 * 典型的权限码对照表：
 * 31(11111):对所有用户开放
 * 30(11110):对已注册用户开放
 * 24(11000):仅对管理员及以上用户开放
 * 28(11100):仅对已经验证邮箱的用户及以上权限开放
 * 4(00100):仅对已经验证邮箱的用户开放
 * 2(00010):仅对尚未验证邮箱，且已经注册的用户开放
 * 1(00001):仅对尚未注册的用户开放
 *
 * 对于权限不足的情况，返回123作为错误码
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
            connection.close();
            System.out.println("code="+code);
            if((code&1)==1){
                return true;
            }else if((code&2)==2&&user!=null&& Objects.equals(user.getRole_id(), "unchecked")){
                return  true;
            }else if((code&4)==4&&user!=null&& Objects.equals(user.getRole_id(), "normal")){
                return true;
            }else if((code&8)==8&&user!=null&&Objects.equals(user.getRole_id(), "admin")){
                return true;
            }else if((code&16)==16&&user!=null&&Objects.equals(user.getRole_id(), "super_admin")){
                return true;
            } else{
                return false;
            }
        }
        connection.close();
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
                sendManager.sendDefaultPermissionError(response);
                return false;
            }
        } catch (SQLException e) {
            exceptionManager.logException(e,Thread.currentThread().getStackTrace()[1].getClassName());
            e.printStackTrace();
        }
        System.out.println("权限不够!");

        sendManager.sendDefaultPermissionError(response);
        return false;
    }
}
