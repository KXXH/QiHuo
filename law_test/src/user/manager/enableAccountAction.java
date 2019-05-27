package user.manager;
import utils.*;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/5/22.
 */
public class enableAccountAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String token=tokenExtractor.extractToken(request);
        System.out.println("token="+token);
        User user=tokenChecker.tokenToUser(token);
        if(user==null|| !Objects.equals(user.getRole_id(), "unchecked")){
            sendManager.sendSimpleErrorJSON(response);
            return;
        }
        try {
            user.setRole_id("normal");
            token=tokenGenerator.getAndStoreToken(user.getUserName());
            HttpSession session=request.getSession();
            session.setAttribute("token",token);
            sendManager.sendSimpleOKJSON(response);
        } catch (SQLException e) {
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
