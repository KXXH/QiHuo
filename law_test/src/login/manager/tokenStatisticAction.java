package login.manager;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/6/6.
 */
public class tokenStatisticAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        long stTimestamp=Long.parseLong(request.getParameter("stTimestamp"));
        long endTimestamp=Long.parseLong(request.getParameter("endTimestamp"));
        if(stTimestamp>endTimestamp){
            sendManager.sendErrorJSONWithMsg(response,"开始时间不能晚于结束时间!");
            return;
        }
        try {
            Connection conn= dbOpener.getDB();
            String sql="SELECT COUNT(*) AS count,TTL FROM tbl_tockeninfo WHERE UNIX_TIMESTAMP(TTL)*1000>=? AND UNIX_TIMESTAMP(TTL)*1000<? AND UNIX_TIMESTAMP(TTL) < UNIX_TIMESTAMP(CURRENT_TIMESTAMP())";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setLong(1,stTimestamp);
            ptmt.setLong(2,endTimestamp);
            System.out.println("tokenStatistic:endTimestamp="+endTimestamp);
            ResultSet rs=ptmt.executeQuery();
            int useless_count=0;
            while(rs.next()){
                useless_count=rs.getInt("count");
                //System.out.println("tokenStatistic:res="+rs.getInt("res"));
                //System.out.println("tokenStatistic:ttl="+rs.getLong("ttl"));
            }
            sql="SELECT COUNT(*) AS count,TTL FROM tbl_tockeninfo WHERE UNIX_TIMESTAMP(TTL)*1000>=? AND UNIX_TIMESTAMP(TTL)*1000<? AND UNIX_TIMESTAMP(TTL) >= UNIX_TIMESTAMP(CURRENT_TIMESTAMP())";
            ptmt=conn.prepareStatement(sql);
            ptmt.setLong(1,stTimestamp);
            ptmt.setLong(2,endTimestamp);
            rs=ptmt.executeQuery();
            int useful_count=0;
            if(rs.next()){
                useful_count=rs.getInt("count");
            }
            sql="SELECT COUNT(*) AS count,Username,CreateAt FROM tbl_userinfo WHERE Username NOT IN (SELECT UserName FROM tbl_tockeninfo) AND UNIX_TIMESTAMP(CreateAt)*1000>=? AND UNIX_TIMESTAMP(CreateAt)<?";
            ptmt=conn.prepareStatement(sql);
            ptmt.setLong(1,stTimestamp);
            ptmt.setLong(2,endTimestamp);
            rs=ptmt.executeQuery();
            int other_count=0;
            if(rs.next()){
                other_count=rs.getInt("count");
            }
            JSONObject json=new JSONObject();
            json.put("status","ok");
            json.put("useful_count",useful_count);
            json.put("useless_count",useless_count);
            json.put("other_count",other_count);
            sendManager.sendJSON(response,json);
        } catch (SQLException e) {

            exceptionManager.logException(e,this, tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            sendManager.sendErrorJSONWithMsg(response,"SQL错误!");
            e.printStackTrace();
        } catch (JSONException e) {
            exceptionManager.logException(e,this, tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            sendManager.sendErrorJSONWithMsg(response,"JSON错误!");
            e.printStackTrace();
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
