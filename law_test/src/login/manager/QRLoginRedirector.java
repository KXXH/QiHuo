package login.manager;

import javax.servlet.http.HttpSession;
import java.util.UUID;
/**
 * Created by zjm97 on 2019/6/4.
 */
public class QRLoginRedirector extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        HttpSession session=request.getSession();
        UUID uuid= UUID.randomUUID();
        session.setAttribute("uuid",uuid.toString());
        response.sendRedirect("https://github.com/login/oauth/authorize?client_id=68e32dfb7606dcbc5881");
    }
}
