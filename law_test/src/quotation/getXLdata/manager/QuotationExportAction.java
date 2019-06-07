package quotation.getXLdata.manager;

import permission.manager.permissionChecker;
import utils.dbOpener;

import javax.servlet.annotation.WebServlet;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 11913 on 2019/6/6.
 */
@WebServlet(name = "QuotationExportAction")
public class QuotationExportAction extends javax.servlet.http.HttpServlet {
    protected String getCSV() {
        try {
            Connection conn = dbOpener.getDB();
            String sql = "SELECT * FROM tbl_quotation";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            StringBuilder stringBuilder = new StringBuilder("id,Code,Name,Quotation,RiseOrFall,ROFper\n");
            ResultSet rs = ptmt.executeQuery();
            while (rs.next()) {
                stringBuilder.append(rs.getInt("id"));
                stringBuilder.append("," + rs.getString("Code"));
                stringBuilder.append("," + rs.getString("Name"));
                stringBuilder.append("," + rs.getDouble("Quotation"));
                stringBuilder.append("," + rs.getDouble("RiseOrFall"));
                stringBuilder.append("," + rs.getDouble("ROFper")+"\n");
            }
            conn.close();
            return stringBuilder.toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "";
        }
    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String csv = getCSV();
        byte[] b = csv.getBytes();
        String filename = "行情导出.csv";
        filename = URLEncoder.encode(filename, "UTF-8");
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
