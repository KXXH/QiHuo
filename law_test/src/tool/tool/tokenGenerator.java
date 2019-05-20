package tool.tool;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

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
        String token = generateToken(userName);
        Connection conn = null;
        try {
            conn = dbOpener.getDB();
            String sql = "SELECT * FROM tbl_tockeninfo WHERE UserName=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userName);
            ResultSet rs = ptmt.executeQuery();
            if(rs.next()) {
                sql = "UPDATE tbl_tockeninfo SET tockenValue=?, TTL=? WHERE UserName=?";
            }
            else{
                sql="INSERT INTO tbl_tockeninfo (tockenValue, TTL,UserName,UserId) VALUES(?,?,?,?)";
            }
            ptmt = conn.prepareStatement(sql);
            ptmt.setString(1, token);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date currentDate = new java.util.Date();
            long timeStamp = currentDate.getTime()+5*60000;
            System.out.println(timeStamp);
            ptmt.setString(2,sdf.format(new java.util.Date(timeStamp)));
            System.out.println("TTL时间是"+sdf.format(new java.util.Date(timeStamp)));
            ptmt.setString(3,userName);
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            return "error";
        }
        return token;
    }
}
