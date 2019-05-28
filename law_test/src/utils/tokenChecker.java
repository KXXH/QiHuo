package utils;
import user.manager.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/4/16.
 * token检查工具类，支持检查token的权限、找到token对应的用户、token对应用户不存在时的重定向
 * 需要注意的是,使用该工具类检查token时,如果token有效,将会自动续期token(5分钟).
 */
public class tokenChecker {
    /**
     * 检查token对应的用户组
     * @param token　需要检查的token
     * @return　用户组名称/"error"/"timeout"
     */
    public static String checkToken(String token){
            try{
                String userType;
                Connection conn = dbOpener.getDB();
                String sql="SELECT * FROM tbl_tockeninfo WHERE tockenValue=?";
                PreparedStatement ptmt = conn.prepareStatement(sql);
                ptmt.setString(1,token);
                ResultSet rs=ptmt.executeQuery();
                if(!rs.next()){
                    System.out.println("没有找到token!");
                    return "error";
                }
                else{
                    String TTL = rs.getString("TTL");
                    int id=rs.getInt("id");
                    java.util.Date date = new java.util.Date();
                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        date = sdf.parse(TTL);
                    }
                    catch (ParseException e)
                    {
                        System.out.println(e.getMessage());
                    }
                    if(date.getTime()<new java.util.Date().getTime()){
                        System.out.println("tocken已过期");
                        System.out.printf("ttl=%d",date.getTime());
                        System.out.printf("time=%d",new java.util.Date().getTime());
                        sql = "DELETE FROM tbl_tockeninfo WHERE tockenValue=?";
                        ptmt = conn.prepareStatement(sql);
                        ptmt.setString(1,token);
                        ptmt.execute();
                        conn.close();
                        return "timeout";
                    }
                    //如果token有效则续期
                    else{
                        System.out.println("token有效!");
                        ptmt = conn.prepareStatement("UPDATE tbl_tockeninfo SET TTL=? WHERE id=?");
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        java.util.Date currentDate = new java.util.Date();
                        long timeStamp = currentDate.getTime()+5*60000;
                        System.out.println(timeStamp);
                        ptmt.setString(1,sdf.format(new java.util.Date(timeStamp)));
                        ptmt.setInt(2,id);
                    }
                    int userId = rs.getInt("UserId");
                    sql="SELECT * FROM tbl_userinfo WHERE userId=?";
                    ptmt = conn.prepareStatement(sql);
                    ptmt.setInt(1,userId);
                    rs = ptmt.executeQuery();
                    if(rs.next()){
                        userType = rs.getString("role_id");
                        conn.close();
                        return userType;
                    }
                    else{
                        System.out.println("没有找到user!");
                        conn.close();
                        return "error";
                    }
                }
            }catch(SQLException e){
                exceptionManager.logException(e,Thread.currentThread().getStackTrace()[1].getClassName());
                e.printStackTrace();
            }
    return "error";
    }

    /**
     * 找到token对应的用户，传入token不存在或token对应用户不存在则返回null
     * 由于返回null的原因，可能会导致空指针，需要注意特别处理
     * @param token　需要检查的token
     * @return　null/对应的用户对象
     */
    public static User tokenToUser(String token){
        try{
            String ans=checkToken(token);
            if(!Objects.equals(ans, "error") || !Objects.equals(ans, "timeout")){
                Connection conn = dbOpener.getDB();
                String sql = "SELECT * FROM tbl_tockeninfo WHERE tockenValue=?";
                PreparedStatement ptmt = conn.prepareStatement(sql);
                ptmt.setString(1,token);
                ResultSet rs = ptmt.executeQuery();
                if(!rs.next()){
                    conn.close();
                    return null;
                }
                else{
                    User user=User.findUser(rs.getString("UserName"),"UserName");
                    conn.close();
                    return user;
                }
            }

        }catch(SQLException e){
            exceptionManager.logException(e,Thread.currentThread().getStackTrace()[1].getClassName());
            e.printStackTrace();
            return null;
        }
        return null;
    }

    /**
     * 还在完善中，目前实现的是token/用户不存在时重定向功能
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    public static User checkTokenAndRedirect(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = tokenExtractor.extractToken(request);
        User user=tokenToUser(token);
        if(user!=null){
            return user;
        }
        else{
            //response.sendRedirect("login");
            sendManager.sendDefaultPermissionError(response);
            return null;
        }
    }
}
