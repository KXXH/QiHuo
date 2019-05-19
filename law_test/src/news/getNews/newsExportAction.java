package news.getNews;

import utils.dbOpener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by 11913 on 2019/4/17.
 */
@WebServlet(name = "newsExportAction")
public class newsExportAction extends HttpServlet {
        protected String getCSV() {
            try {
                Connection conn = dbOpener.getDB();
                String sql = "SELECT * FROM tbl_news";
                PreparedStatement ptmt = conn.prepareStatement(sql);
                StringBuilder stringBuilder = new StringBuilder("id,title,date,author_name\n");
                ResultSet rs = ptmt.executeQuery();
                while (rs.next()) {
                    stringBuilder.append(rs.getInt("id"));
                    stringBuilder.append("," + rs.getString("title"));
                    stringBuilder.append("," + rs.getString("date"));
                    stringBuilder.append("," + rs.getString("author_name")+"\n");
                }
                return stringBuilder.toString();
            } catch (SQLException e) {
                e.printStackTrace();
                return "";
            }
        }

        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String csv = getCSV();
            byte[] b = csv.getBytes();
            String filename = "导出.csv";
            filename = URLEncoder.encode(filename, "UTF-8");
            response.setContentType("file/csv");
            response.setHeader("Content-Disposition", "attachment;filename=" + filename);
            OutputStream output = response.getOutputStream();
            output.write(b);
            output.close();
        }

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request,response);
        }
}
