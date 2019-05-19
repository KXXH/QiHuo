package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.*;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/4/17.
 */
public class dataToCSV extends javax.servlet.http.HttpServlet {

    protected String getCSV(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
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
            return stringBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "";
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String token=request.getParameter("token");
        String user_role= utils.tokenChecker.checkToken(token);
        if(!Objects.equals(user_role, "admin")){
            try{
                JSONObject json = new JSONObject();
                json.put("status","error");
                response.setContentType("application/json; charset=UTF-8");
                try{
                    response.getWriter().print(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                return;
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
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
