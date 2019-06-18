package transaction.bmma.change;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.*;


@WebServlet(name = "bmmachangeAction")
public class bmmachangeAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        String tocken= tokenExtractor.extractToken(request);

        User user = tokenChecker.tokenToUser(tocken);

        String createat = request.getParameter("CreateAt");
        int orderid = Integer.parseInt(request.getParameter("OrderId"));
        JSONObject jsonObject = new JSONObject();
        try {
            Connection conn = dbOpener.getDB();
            //String sql = "UPDATE tbl_news SET title=?, date=?, author_name=? WHERE id=?";
            String sql = "update tbl_bm set CreateAt='" + createat + "' WHERE OrderId='" + orderid + "'";
            System.out.println(sql);
            Statement statement = conn.createStatement();
            //PreparedStatement ptmt = conn.prepareStatement(sql);
            //ptmt = conn.prepareStatement(sql);
            /*ptmt.setString(1, title);
            ptmt.setString(2, date);
            ptmt.setString(3, author_name);
            ptmt.setString(4, id);
            ptmt.execute();*/
            statement.executeUpdate(sql);
            statement.close();
            conn.close();
        } catch (SQLException e) {
            try {
                jsonObject.put("status","0");
                try {
                    response.getWriter().print(jsonObject);
                    response.getWriter().flush();
                    response.getWriter().close();
                } catch (IOException et) {
                    et.printStackTrace();
                }
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return;
        }
        try {
            jsonObject.put("status","1");
            response.setContentType("application/json; charset=UTF-8");
            try {
                response.getWriter().print(jsonObject);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

