package trade.menu;

import utils.dbOpener;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * Created by zjm97 on 2019/3/30.
 */
@WebServlet(name = "logoutAction")
public class logoutAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = (String)request.getSession().getAttribute("token");

        System.out.println("执行了post");
        try{
            Connection conn = dbOpener.getDB();
            String sql="DELETE FROM tbl_tockeninfo WHERE tockenValue=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,token);
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        System.out.println("删除了cookie");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
