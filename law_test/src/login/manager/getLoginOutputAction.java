package login.manager;

import permission.manager.permissionChecker;
import utils.*;

import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zjm97 on 2019/6/6.
 */
public class getLoginOutputAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        long stTimestamp=Long.parseLong(request.getParameter("startTime"));
        long endTimestamp=Long.parseLong(request.getParameter("endTime"));
        if(stTimestamp>endTimestamp){
            sendManager.sendErrorJSONWithMsg(response,"结束时间必须晚于开始时间!");
            return;
        }
        try {
            Connection conn= dbOpener.getDB();
            String sql="SELECT * FROM tbl_logininfo WHERE UNIX_TIMESTAMP(time)*1000>=? AND UNIX_TIMESTAMP(time)*1000<?";
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setLong(1,stTimestamp);
            ptmt.setLong(2,endTimestamp);
            ResultSet rs=ptmt.executeQuery();
            StringBuilder stringBuilder = new StringBuilder("id,userName,time,action,ip,location\n");
            while(rs.next()){
                stringBuilder.append(rs.getInt("id"));
                stringBuilder.append(","+rs.getString("userName"));
                stringBuilder.append(","+rs.getString("time"));
                stringBuilder.append(","+rs.getString("action"));
                stringBuilder.append(","+rs.getString("ip"));
                stringBuilder.append(","+rs.getString("location")+"\n");
            }
            String csv=stringBuilder.toString();
            byte[] b=csv.getBytes();
            String filename="登录记录导出.csv";
            filename= URLEncoder.encode(filename,"UTF-8");
            response.setContentType("file/csv");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            OutputStream output = response.getOutputStream();
            output.write(b);
            output.close();
        } catch (SQLException e) {
            exceptionManager.logException(e,this, tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }
}
