package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.sql.SQLException;
import utils.*;
/**
 * Created by zjm97 on 2019/5/15.
 */
public class resetPasswordAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String token = tokenExtractor.extractToken(request);
        String newPassword = request.getParameter("newPassword");
        User user= utils.tokenChecker.tokenToUser(token);
        JSONObject json = new JSONObject();
        if(user==null){
            try {
                json.put("error","can not find user!");
                json.put("status","error");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            token= utils.tokenGenerator.getAndStoreToken(user.getUserName());
            try {
                user.setPasswd(newPassword);
                json.put("status","ok");
                json.put("token",token);
            } catch (SQLException e) {
                try {
                    json.put("error","SQL database error!");
                    json.put("status","error");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        sendManager.sendJSON(response,json);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
