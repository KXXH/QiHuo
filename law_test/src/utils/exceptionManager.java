package utils;

import user.manager.User;

import javax.servlet.http.HttpServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * Created by zjm97 on 2019/5/27.
 */
public class exceptionManager {
    public static void logException(Exception e,Object o){
        try {
            Connection conn=dbOpener.getDB();
            String sql="INSERT INTO tbl_exceptions (className,exceptionName,exceptionDetail,status,time,username) VALUES(?,?,?,?,?,?)";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date currentDate = new java.util.Date();
            long timeStamp = currentDate.getTime();
            System.out.println(timeStamp);
            if(o instanceof String){
                ptmt.setString(1,(String)o);
            }else{
                ptmt.setString(1,o.getClass().getCanonicalName());
            }
            ptmt.setString(2,e.getClass().getCanonicalName());
            ptmt.setString(3,e.getMessage());
            ptmt.setString(4,"unchecked");
            ptmt.setString(5,sdf.format(new java.util.Date(timeStamp)));
            ptmt.setString(6,"unknown");
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }
    public static void logException(Exception e,Object o,User user){
        try {
            Connection conn=dbOpener.getDB();
            String sql="INSERT INTO tbl_exceptions (className,exceptionName,exceptionDetail,status,time,username) VALUES(?,?,?,?,?,?)";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            java.util.Date currentDate = new java.util.Date();
            long timeStamp = currentDate.getTime();
            System.out.println(timeStamp);
            ptmt.setString(1,o.getClass().getCanonicalName());
            ptmt.setString(2,e.getClass().getCanonicalName());
            ptmt.setString(3,e.getMessage());
            ptmt.setString(4,"unchecked");
            ptmt.setString(5,sdf.format(new java.util.Date(timeStamp)));
            if(user!=null){
                ptmt.setString(6,user.getUserName());
            }else{
                ptmt.setString(6,"null");
            }
            ptmt.execute();
            ptmt.close();
            conn.close();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
    }

}
