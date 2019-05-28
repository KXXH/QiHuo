package utils;

import user.manager.User;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

/**
 * Created by zjm97 on 2019/5/15.
 */
public class tokenGenerator {
    public static String generateToken(String userName){
        Date date = new Date();
        try {
            return Base64.getEncoder().encodeToString((userName+String.valueOf(date.getTime())).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
    }

    public static String getAndStoreToken(String userName){
        return getAndStoreToken(userName,false);
    }

    /**
     * 生成并储存token，然后返回这个token。由于有记住密码的差别，token存活时间可以长至３０天，也可以短到20分钟，由参数shortRoLong控制
     * @param userName
     * @param shortOrLong 长时间有效token还是短时间有效token?
     * @return
     */
    public static String getAndStoreToken(String userName,boolean shortOrLong){
        String token = generateToken(userName);
        Connection conn = null;
        try {
            conn = dbOpener.getDB();
            String sql = "SELECT * FROM tbl_tockeninfo WHERE UserName=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userName);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()) {
                sql = "UPDATE tbl_tockeninfo SET tockenValue=?, TTL=? WHERE UserName=? AND UserId=?";
            }
            else{
                sql="INSERT INTO tbl_tockeninfo (tockenValue, TTL,UserName,UserId) VALUES(?,?,?,?)";
            }
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, token);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date currentDate = new java.util.Date();
            long timeStamp = currentDate.getTime()+20*60000;
            if(shortOrLong){
                timeStamp+=60*1000*60*24*10;
            }
            System.out.println(timeStamp);
            ptmt.setString(2,sdf.format(new java.util.Date(timeStamp)));
            System.out.println("TTL时间是"+sdf.format(new java.util.Date(timeStamp)));
            ptmt.setString(3,userName);
            ptmt.setInt(4, User.findUser(userName,"UserName").getUserId());
            System.out.println("执行的SQL语句是"+ptmt.toString());
            ptmt.execute();
            conn.close();
        } catch (SQLException e) {
            exceptionManager.logException(e,Thread.currentThread().getStackTrace()[1].getClassName());
            e.printStackTrace();
            return "error";
        }
        return token;
    }
}
