package login.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.*;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/5/24.
 */
public class getLoginRecordAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        HttpSession session=request.getSession();
        int count=0;
        if(request.getParameter("count")!=null){
            count=Integer.parseInt(request.getParameter("count"));
        }
        int rowCount=0;
        int page_size=15;
        if(request.getParameter("page_size")!=null){
            page_size=Integer.parseInt(request.getParameter("page_size"));
        }
        String user=request.getParameter("username");
        String sort_by_1=request.getParameter("sort_by_1");
        String sort_by_2=request.getParameter("sort_by_2");
        int int_asc_1=Integer.parseInt(request.getParameter("asc_1"));
        int int_acs_2=Integer.parseInt(request.getParameter("asc_2"));
        User opUser= tokenChecker.tokenToUser(tokenExtractor.extractToken(request));
        //超级管理员可以查看所有用户的登录记录,而普通用户只能察看自己的登录记录
        if(!Objects.equals(user, opUser.getUserName())&&!Objects.equals(opUser.getRole_id(), "super_admin")){
            sendManager.sendDefaultPermissionError(response);
        }
        switch(sort_by_1){
            case "id":case "userName":case "time":case "ip":case "location":break;
            default:sendManager.sendErrorJSONWithMsg(response,"错误:排序关键字非法!");return;
        }
        switch(sort_by_2){
            case "id":case "userName":case "time":case "ip":case "location":break;
            default:sendManager.sendErrorJSONWithMsg(response,"错误:排序关键字非法!");return;
        }
        String asc_1="DSEC";
        String asc_2="ASC";
        if(int_asc_1>0){
            asc_1="ASC";
        }else{
            asc_1="DESC";
        }
        if(int_acs_2>0){
            asc_2="ASC";
        }else{
            asc_2="DESC";
        }
        System.out.println("count="+count);
        try {
            Connection conn= dbOpener.getDB();
            String sql=null;
            if(user==null||user.length()==0)
                sql="SELECT * FROM tbl_logininfo ORDER BY "+sort_by_1+" "+asc_1+" , "+sort_by_2+" "+asc_2+" LIMIT ? OFFSET ? ";
            else
                sql="SELECT * FROM tbl_logininfo WHERE userName=? ORDER BY "+sort_by_1+" "+asc_1+" , "+sort_by_2+" "+asc_2+" LIMIT ? OFFSET ? ";
            PreparedStatement ptmt=conn.prepareStatement(sql);

            if(!(user==null||user.length()==0)){
                ptmt.setString(1,user);
                ptmt.setInt(2,page_size);
                ptmt.setInt(3,count);
            }else{
                ptmt.setInt(1,page_size);
                ptmt.setInt(2,count);
            }
            System.out.println("最终执行的SQL是 "+ptmt.toString());
            ResultSet rs=ptmt.executeQuery();
            JSONArray array=new JSONArray();

            while(rs.next()){
                int id=rs.getInt("id");
                String username=rs.getString("userName");
                System.out.println("username="+username);
                String time=rs.getString("time");
                String action=rs.getString("action");
                String ip=rs.getString("ip");
                String location=rs.getString("location");
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("username",username);
                jsonObject.put("time",time);
                jsonObject.put("action",action);
                jsonObject.put("ip",ip);
                jsonObject.put("id",id);
                jsonObject.put("location",location);
                array.put(jsonObject);
                count++;
                System.out.println("行数="+rs.getRow());
                if(rs.getRow()>rowCount)rowCount=rs.getRow();
            }
            System.out.println("行数="+rs.getRow());
            if(array.length()==0){
                JSONObject j=new JSONObject();
                j.put("status","end");
                sendManager.sendJSON(response,j);
                conn.close();
                return;
            }
            //session.setAttribute("count",count);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("status","ok");

            jsonObject.put("data",array);
            jsonObject.put("count",count);
            assert response != null;
            sendManager.sendJSON(response,jsonObject);
            conn.close();
        } catch (SQLException e) {
            assert response != null;
            sendManager.sendErrorJSONWithMsg(response,"SQL 错误");
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        } catch (JSONException e) {
            assert response != null;
            sendManager.sendErrorJSONWithMsg(response,"JSON 解析错误");

            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
