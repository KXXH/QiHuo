package user.manager;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Objects;
import utils.*;
/**
 * Created by zjm97 on 2019/4/17.
 */
public class dataToCSV extends javax.servlet.http.HttpServlet {

    protected String getCSV(){
        try {
            Connection conn = dbOpener.getDB();
            String sql = "SELECT * FROM tbl_userinfo";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            StringBuilder stringBuilder = new StringBuilder("id,username,email,phone,wechat_id,create_at,role_id\n");
            ResultSet rs=ptmt.executeQuery();
            while(rs.next()){
                stringBuilder.append(rs.getInt("UserId"));
                stringBuilder.append(","+rs.getString("UserId"));
                stringBuilder.append(","+rs.getString("Email"));
                stringBuilder.append(","+rs.getString("Phone"));
                stringBuilder.append(","+rs.getString("WeChatId"));
                stringBuilder.append(","+rs.getString("CreateAt"));
                stringBuilder.append(","+rs.getString("role_id")+"\n");
            }
            conn.close();
            return stringBuilder.toString();
        } catch (SQLException e) {
            exceptionManager.logException(e,this);
            e.printStackTrace();
        }
        return "";
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String token=tokenExtractor.extractToken(request);
        User user=tokenChecker.tokenToUser(token);
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String csv = getCSV();
        byte[] b=csv.getBytes();
        String filename="导出.csv";
        filename= URLEncoder.encode(filename,"UTF-8");
        response.setContentType("file/csv");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        OutputStream output = response.getOutputStream();
        output.write(b);
        output.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        doPost(request,response);
    }
}
