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
public class getTokenOutputAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }


    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String strStartTimestamp=request.getParameter("startTime");
        String strEndTimestamp=request.getParameter("endTime");
        long startTimestamp=0,endTimestamp=0;

        if(strStartTimestamp!=null&&strEndTimestamp!=null){
            startTimestamp=Long.parseLong(strStartTimestamp);
            endTimestamp=Long.parseLong(strEndTimestamp);
            if(startTimestamp>endTimestamp){
                sendManager.sendErrorJSONWithMsgAndCode(response,"起始日期不能超过结束日期!",29);
                return;
            }
            System.out.println("getTokenOutputAction:startTimeStamp="+startTimestamp);
            System.out.println("getTokenOutputAction:endTimeStamp="+endTimestamp);
            try {
                Connection conn= dbOpener.getDB();
                String sql="SELECT *,UNIX_TIMESTAMP(TTL) AS stamp FROM tbl_tockeninfo WHERE UNIX_TIMESTAMP(TTL)*1000-?>=0 AND UNIX_TIMESTAMP(TTL)*1000-?<0 ";
                PreparedStatement ptmt=conn.prepareStatement(sql);
                ptmt.setLong(1,startTimestamp);
                ptmt.setLong(2,endTimestamp);
                ResultSet rs=ptmt.executeQuery();
                StringBuilder stringBuilder = new StringBuilder("id,tokenValue,UserName,UserId,TTL\n");
                while(rs.next()){
                    stringBuilder.append(rs.getInt("id"));
                    stringBuilder.append(","+rs.getString("tockenvalue"));
                    stringBuilder.append(","+rs.getString("UserName"));
                    stringBuilder.append(","+rs.getString("userId"));
                    stringBuilder.append(","+rs.getString("TTL")+"\n");
                    System.out.println("getTokenOutputAction:stamp="+rs.getLong("stamp"));
                }
                String csv=stringBuilder.toString();
                byte[] b=csv.getBytes();
                String filename="token导出.csv";
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
        }else{
            sendManager.sendErrorJSONWithMsg(response,"必须输入起始时间和结束时间!");
        }
    }
}
