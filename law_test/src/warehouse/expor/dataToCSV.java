package warehouse.expor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/4/17.
 */
public class dataToCSV extends javax.servlet.http.HttpServlet {

    protected String getCSV(){
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            String sql = "SELECT * FROM tbl_userwh";
            System.out.println("即将执行的SQL语句是：" + sql);
            PreparedStatement ptmt = conn.prepareStatement(sql);
            StringBuilder stringBuilder = new StringBuilder("id,stockname,quantity,bunitprice\n");
            ResultSet rs=ptmt.executeQuery();
            while(rs.next()){
                stringBuilder.append(rs.getInt("StockId"));
                stringBuilder.append(","+rs.getString("StockName"));
                stringBuilder.append(","+rs.getString("Quantity"));
                stringBuilder.append(","+rs.getDouble("BUnitPrice")+"\n");
                System.out.println();

            }
            return stringBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("Database Closed！！！");
        return "";
    }

    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        
        String csv = getCSV();
        byte[] b=csv.getBytes();
        System.out.println("!!!!!!！！！");
        String filename="导出.csv";
        filename= URLEncoder.encode(filename,"UTF-8");
        response.setContentType("file/csv");
        response.setHeader("Content-Disposition", "attachment;filename=" + filename);
        OutputStream output = response.getOutputStream();
        System.out.println("1111111");
        output.write(b);
        output.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        doPost(request,response);
    }
}
