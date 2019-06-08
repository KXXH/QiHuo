package login.manager;

import utils.cookieManager;

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
        //UUID uuid= UUID.randomUUID();
        String uuid=request.getParameter("uuid");
        System.out.println("QRLoginRedirector:uuid="+uuid);
        session.setAttribute("uuid",uuid);
        //cookieManager.addCookie(response,"uuid",uuid,300);
        QRLoginChecker.setQRStatus(uuid,"scaned");
        response.sendRedirect("https://github.com/login/oauth/authorize?client_id=68e32dfb7606dcbc5881&redirect_uri=http://127.0.0.1:8080/XM10/githubLogin/"+uuid);
        System.out.println("https://github.com/login/oauth/authorize?client_id=68e32dfb7606dcbc5881&redirect_uri=http://127.0.0.1:8080/XM10/githubLogin/"+uuid);
    }
}
