package login.manager;

import user.manager.User;
import utils.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by zjm97 on 2019/5/22.
 */
public class loginRecorder {

    public static void loginWithOtherAction(User user, String action, HttpServletRequest request){
        System.out.println("记录用户登录...");
        Connection conn= null;
        try {
            conn = dbOpener.getDB();
            String sql="INSERT INTO tbl_logininfo (userName,time,action,ip) VALUES(?,?,?,?)";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date ddate=new java.util.Date();
            String sdate=simpleDateFormat.format(ddate);
            ptmt.setString(1,user.getUserName());
            ptmt.setString(2,sdate);
            ptmt.setString(3,action);
            ptmt.setString(4,ipExtractor.getIpAddr(request));
            ptmt.execute();
            conn.close();
        } catch (SQLException e) {
            exceptionManager.logException(e,Thread.currentThread().getStackTrace()[1].getClassName());
            e.printStackTrace();
        }
    }
    public static void loginWithPassword(User user, HttpServletRequest request){
        loginWithOtherAction(user,"login with password",request);
    }
    public static void loginWithToken(User user,HttpServletRequest request){
        loginWithOtherAction(user,"login with token",request);
    }
    public static void loginWithToken(HttpServletRequest request){
        loginWithOtherAction(tokenChecker.tokenToUser(tokenExtractor.extractToken(request)),"login with token",request);
    }
}
