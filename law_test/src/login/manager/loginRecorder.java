package login.manager;

import mailService.sendMail;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import user.manager.User;
import utils.*;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/5/22.
 */
public class loginRecorder {

    public static void loginWithOtherAction(User user, String action, HttpServletRequest request){
        System.out.println("记录用户登录...");
        Connection conn= null;
        try {
            conn = dbOpener.getDB();
            String ip=ipExtractor.getIpAddr(request);
            //ip="110.221.32.1";
            String location=networkOpener.getResponse("http://ip.taobao.com/service/getIpInfo.php?ip="+ip,"utf-8");

            if(location.length()>0){
                JSONObject j=new JSONObject(location);
                location=j.getJSONObject("data").getString("country")+" "+j.getJSONObject("data").getString("region")+" "+j.getJSONObject("data").getString("city");
            }else{
                location="未知";
            }
            String sql="INSERT INTO tbl_logininfo (userName,time,action,ip,location) VALUES(?,?,?,?,?)";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date ddate=new java.util.Date();
            String sdate=simpleDateFormat.format(ddate);
            ptmt.setString(1,user.getUserName());
            ptmt.setString(2,sdate);
            ptmt.setString(3,action);
            ptmt.setString(4,ip);
            ptmt.setString(5,location);
            ptmt.execute();
            sql="SELECT location FROM tbl_logininfo WHERE userName=? ORDER BY time DESC LIMIT 1 OFFSET 1";
            ptmt=conn.prepareStatement(sql);
            ptmt.setString(1,user.getUserName());
            ResultSet rs=ptmt.executeQuery();
            if(rs.next()){
                String lastLocation=rs.getString("location");
                if(!Objects.equals(lastLocation, location)){
                    sendMail.sendOffsiteLoginEmail(user.getEmail(),location,ip,user.getUserName());
                }
            }
            conn.close();
        } catch (SQLException |JSONException |GeneralSecurityException e) {
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
