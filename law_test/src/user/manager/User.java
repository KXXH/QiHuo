package user.manager;

import utils.dbOpener;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public static User login(String userName,String passwd){
        try {
            Connection conn = dbOpener.getDB();
            String sql="SELECT * FROM tbl_userinfo WHERE UserName=? AND Passwd=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userName);
            ptmt.setString(2,passwd);
            ResultSet rs = ptmt.executeQuery();
            if(!rs.next()){
                conn.close();
                return null;
            }
            else{
                conn.close();
                return findUser(userName,"UserName");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
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

    public static User findUserById(int target){
        try {
            Connection conn = conn = dbOpener.getDB();
            String sql=getQuerySQL("UserId");
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setInt(1,target);
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

    public boolean delUser() throws SQLException {
        Connection conn = utils.dbOpener.getDB();
        String sql = "DELETE FROM tbl_userinfo WHERE UserId=?";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setInt(1,UserId);
        ptmt.execute();
        sql="DELETE FROM tbl_tokeninfo WHERE UserId=?";
        ptmt=conn.prepareStatement(sql);
        ptmt.setInt(1,UserId);
        ptmt.execute();
        conn.close();
        return true;
    }

    public static User addUser(String userName,String passwd,String email,String phone,String wechatId,String role_id) throws SQLException {
        Connection conn = dbOpener.getDB();
        User user=findUser(userName,"UserName");
        if(user!=null){
            return null;
        }
        user=findUser(userName,"Email");
        if(user!=null){
            return null;
        }
        String sql="INSERT INTO tbl_userinfo (UserName,Passwd,Email,Phone,WeChatId,CreateAt,role_id) VALUES(?,?,?,?,?,?,?)";
        int id=0;
        PreparedStatement ptmt = conn.prepareStatement(sql);
        System.out.println("SQL语句是:"+sql);
        ptmt.setString(1,userName);
        ptmt.setString(2,passwd);
        ptmt.setString(3,email);
        ptmt.setString(4,phone);
        ptmt.setString(5,wechatId);
        ptmt.setString(7,role_id);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date ddate=new java.util.Date();
        String sdate=simpleDateFormat.format(ddate);
        ptmt.setString(6,sdate);
        System.out.println("真正执行的SQL是"+ptmt.toString());
        ptmt.execute();
        sql="SELECT * FROM tbl_userinfo WHERE USERNAME=?";

        ptmt=conn.prepareStatement(sql);
        ptmt.setString(1,userName);
        ResultSet rs=ptmt.executeQuery();
        if(rs.next()){
            id=rs.getInt("UserId");
        }
        user=new User(id,userName,passwd,email,phone,wechatId,sdate,role_id);
        return user;

    }

    public int getUserId() {
        return UserId;
    }


    public String getUserName() {
        return UserName;
    }

    public boolean setUserName(String userName) throws SQLException {
        if(Objects.equals(userName, getUserName())){return true;}
        User user=findUser(userName,"UserName");
        if(user!=null){
            return false;
        }
        executeUpdate(userName,"UserName");
        UserName = userName;
        return true;
    }


    public void setPasswd(String passwd) throws SQLException {
        executeUpdate(passwd,"Passwd");
        Passwd = passwd;
    }

    public String getEmail() {
        return Email;
    }

    public boolean setEmail(String email) throws SQLException {
        if(Objects.equals(getEmail(), email)){return true;}
        User user=findUser(email,"Email");
        if(user!=null){
            return false;
        }
        executeUpdate(email,"Email");
        Email = email;
        return true;
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

    public boolean setRole_id(String role_id,User opUser) throws SQLException {
        switch(role_id){
            case "unchecked":case "normal":
                switch(opUser.getRole_id()){
                    case "admin":case "super_admin":
                        setRole_id(role_id);
                        return true;
                    default:
                        return false;
                }
            case "admin":
                switch(opUser.getRole_id()){
                    case "super_admin":
                        setRole_id(role_id);
                        return true;
                    default:
                        return false;
                }
            case "super_admin":default:
                return false;
        }
    }
}
