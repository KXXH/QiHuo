package trade.menu;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjm97 on 2019/3/30.
 */
@WebServlet(name = "logoutAction")
public class logoutAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String tocken = request.getParameter("tocken");
        System.out.println("执行了post");

        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException classNotFoundException){
            classNotFoundException.printStackTrace();
        }
        try{
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=Zjm,,971014&useUnicode=true&characterEncoding=UTF-8");
            String sql="DELETE FROM tbl_tockeninfo WHERE tockenValue=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,tocken);
            ptmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("删除了cookie");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
