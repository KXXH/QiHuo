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
        String user=request.getParameter("username");
        String lastUser=(String)session.getAttribute("lastUsername");
        User opUser= tokenChecker.tokenToUser(tokenExtractor.extractToken(request));
        /*
        if((user==null||user.length()==0)&&(lastUser==null||lastUser.length()==0)){
            //上次访问未指定用户且本次访问未指定用户,则按照标准惰性加载
        }else if(!(user==null||user.length()==0)&&(lastUser==null||lastUser.length()==0)){
            //上次访问未指定用户但本次访问指定了用户,则清空惰性加载记忆
            count=0;
        }else if((user==null||user.length()==0)&&!(lastUser==null||lastUser.length()==0)){
            //上次访问指定用户但本次访问未指定用户,则清空惰性加载记忆
            count=0;
        }else if(!(user==null||user.length()==0)&&!(lastUser==null||lastUser.length()==0)&&!user.equals(lastUser)){
            //如果上次访问和本次访问都指定了用户且它们不同,则清空惰性加载记忆
            count=0;
        }else if(!(user==null||user.length()==0)&&!(lastUser==null||lastUser.length()==0)&&user.equals(lastUser)){
            //否则,则不改变惰性加载记忆
        }*/
        if(!Objects.equals(user, opUser.getUserName())&&!Objects.equals(opUser.getRole_id(), "super_admin")){
            sendManager.sendDefaultPermissionError(response);
        }
        System.out.println("count="+count);
        try {
            Connection conn= dbOpener.getDB();
            String sql=null;
            if(user==null||user.length()==0)
                sql="SELECT * FROM tbl_logininfo ORDER BY id DESC LIMIT ? OFFSET ? ";
            else
                sql="SELECT * FROM tbl_logininfo WHERE userName=? ORDER BY id DESC LIMIT ? OFFSET ? ";
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
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("username",username);
                jsonObject.put("time",time);
                jsonObject.put("action",action);
                jsonObject.put("ip",ip);
                jsonObject.put("id",id);
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
