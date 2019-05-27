package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import permission.manager.permissionChecker;
import utils.*;
/**
 * Created by zjm97 on 2019/4/15.
 */
public class queryAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("执行了POST--user.manager");
        String username = request.getParameter("username");
        System.out.println("username="+username);
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String token = tokenExtractor.extractToken(request);
        System.out.println("token="+token);
        String user_role= utils.tokenChecker.checkToken(token);
        String sorted_by = request.getParameter("sorted_by");
        String sorted_by2=request.getParameter("sorted_by2");
        System.out.println("user_role="+user_role);
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        if(sorted_by==null||sorted_by2==null){
            sendManager.sendSimpleErrorJSON(response);
            return;
        }
        try{
            Connection conn = utils.dbOpener.getDB();
            boolean flag=false;
            boolean usernameFlag=false,emailFlag=false,phoneFlag=false;
            int count=1;
            StringBuilder stringBuilder = new StringBuilder("SELECT * FROM tbl_userinfo ");
            System.out.println("执行到这里了");
            if(username!=null&&!username.isEmpty()){
                System.out.println("执行到这里了1");
                usernameFlag=flag=true;

                stringBuilder.append("WHERE UserName=? ");
            }
            if(email!=null&&!email.isEmpty()){
                System.out.println("执行到这里了2");
                if(flag){
                    stringBuilder.append("AND ");
                }
                else{
                    stringBuilder.append("WHERE ");
                }
                emailFlag=flag=true;
                stringBuilder.append("Email=? ");
            }
            if(phone!=null&&!phone.isEmpty()){
                System.out.println("执行到这里了3");
                if(flag){
                    stringBuilder.append("AND ");
                }
                else{
                    stringBuilder.append("WHERE ");
                }
                phoneFlag=flag=true;
                stringBuilder.append("Phone=? ");
            }
            stringBuilder.append("ORDER BY ");
            if(Objects.equals(sorted_by, "UserId")){
                stringBuilder.append("UserId");
            }
            else if(Objects.equals(sorted_by, "UserName")){
                stringBuilder.append("UserName");
            }
            else if(Objects.equals(sorted_by, "Email")){
                stringBuilder.append("Email");
            }
            else if(Objects.equals(sorted_by, "Phone")){
                stringBuilder.append("Phone");
            }
            else if(Objects.equals(sorted_by, "CreateAt")){
                stringBuilder.append("CreateAt");
            }
            if(Objects.equals(sorted_by2, "UserId")){
                stringBuilder.append(",UserId");
            }
            else if(Objects.equals(sorted_by2, "UserName")){
                stringBuilder.append(",UserName");
            }
            else if(Objects.equals(sorted_by2, "Email")){
                stringBuilder.append(",Email");
            }
            else if(Objects.equals(sorted_by2, "Phone")){
                stringBuilder.append(",Phone");
            }
            else if(Objects.equals(sorted_by2, "CreateAt")){
                stringBuilder.append(",CreateAt");
            }
            System.out.println("conn是null吗?"+Objects.equals(conn,null));
            PreparedStatement ptmt = conn.prepareStatement(stringBuilder.toString());
            System.out.println("SQL语句是:"+stringBuilder.toString());
            if(usernameFlag){
                ptmt.setString(count++,username);
            }
            if(emailFlag){
                ptmt.setString(count++,email);
            }
            if(phoneFlag){
                ptmt.setString(count++,phone);
            }
            System.out.println("真正执行的SQL是"+ptmt.toString());
            ResultSet rs = ptmt.executeQuery();
            List jsonList = new ArrayList();
            while(rs.next()){
                JSONObject json = new JSONObject();
                json.put("user_id",rs.getInt("UserId"));
                json.put("username",rs.getString("UserName"));
                json.put("email",rs.getString("Email"));
                json.put("phone",rs.getString("Phone"));
                json.put("wechat_id",rs.getString("WechatId"));
                json.put("create_at",rs.getString("CreateAt"));
                json.put("role_id",rs.getString("role_id"));
                jsonList.add(json);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("status","ok");
            jsonObject.put("list",jsonList);
            sendManager.sendJSON(response,jsonObject);
            return;

        }catch (SQLException | JSONException e){
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
