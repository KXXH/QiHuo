package tool.tool;

import java.sql.*;

/**
 * Created by zjm97 on 2019/5/15.
 */
public class User {
    private int UserId;
    private String UserName;
    private String Passwd;
    private String Email;
    private String Phone;
    private String WechatId;
    private String CreateAt;
    private String role_id;
    public User(int id,String username,String password, String email, String phone, String wechatId, String createAt, String role){
        UserId=id;
        UserName=username;
        Passwd=password;
        Email=email;
        Phone=phone;
        WechatId=wechatId;
        CreateAt=createAt;
        role_id=role;
    }
    private static String getQuerySQL(String column){
        return "SELECT * FROM tbl_userinfo WHERE "+column+"=?";
    }
    private void executeUpdate(String target,String column) throws SQLException {
        Connection conn = dbOpener.getDB();
        String sql="UPDATE tbl_userinfo SET "+column+"=? WHERE UserId=?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1,target);
        ptmt.setInt(2,UserId);
        ptmt.execute();
        conn.close();
    }
    public static boolean login(String userName,String passwd){
        try {
            Connection conn = dbOpener.getDB();
            String sql="SELECT * FROM tbl_userinfo WHERE UserName=? AND Passwd=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userName);
            ptmt.setString(2,passwd);
            ResultSet rs = ptmt.executeQuery();
            if(!rs.next()){
                conn.close();
                return false;
            }
            else{
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
    public static User findUser(String target,String column){
        try {
            Connection conn = conn = dbOpener.getDB();
            String sql=getQuerySQL(column);
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,target);
            ResultSet rs=ptmt.executeQuery();
            if(!rs.next()){
                conn.close();
                return null;
            }
            else{
                User user = new User(
                        rs.getInt("UserId"),rs.getString("UserName"),rs.getString("Passwd"),
                        rs.getString("Email"),rs.getString("Phone"),rs.getString("WechatId"),
                        rs.getString("CreateAt"),rs.getString("role_id")
                );
                conn.close();
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getUserId() {
        return UserId;
    }


    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) throws SQLException {
        executeUpdate(userName,"UserName");
        UserName = userName;
    }


    public void setPasswd(String passwd) throws SQLException {
        executeUpdate(passwd,"Passwd");
        Passwd = passwd;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) throws SQLException {
        executeUpdate(email,"Email");
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) throws SQLException {
        executeUpdate(phone,"Phone");
        Phone = phone;
    }

    public String getWechatId() {
        return WechatId;
    }

    public void setWechatId(String wechatId) throws SQLException {
        executeUpdate(wechatId,"WechatId");
        WechatId = wechatId;
    }

    public String getCreateAt() {
        return CreateAt;
    }


    public String getRole_id() {
        return role_id;
    }

    public void setRole_id(String role_id) throws SQLException {
        executeUpdate(role_id,"role_id");
        this.role_id = role_id;
    }
}
