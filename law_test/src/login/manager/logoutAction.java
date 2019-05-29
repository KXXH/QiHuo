package login.manager;

import user.manager.User;
import utils.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

/**
 * Created by zjm97 on 2019/3/30.
 */
@WebServlet(name = "logoutAction")
public class logoutAction extends HttpServlet{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String token = (String)request.getSession().getAttribute("token");

        System.out.println("执行了post");
        try{
            Connection conn = dbOpener.getDB();
            String sql="DELETE FROM tbl_tockeninfo WHERE tockenValue=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,token);
            ptmt.execute();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        HttpSession session=request.getSession();
        try{
            session.invalidate();
            cookieManager.getCookieByName(request,"JSESSIONID").setMaxAge(0);
            Cookie cookie=cookieManager.getCookieByName(request,"token");
            Cookie[] a=request.getCookies();
            for(int i=0;i<a.length;i++){
                System.out.println("name: "+a[i].getName());
            }
            if(cookie!=null){
                cookie.setMaxAge(0);
                System.out.println("删除了cookie");
            }
        }catch(IllegalStateException ignored){
            exceptionManager.logException(ignored,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


}
