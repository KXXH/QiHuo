package chat;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;

import javax.mail.Session;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;
import utils.tokenChecker;
import utils.sendManager;
/**
 * Created by 11913 on 2019/5/30.
 */
@WebServlet(name = "ChatGetUserName")
public class ChatGetUserName extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        HttpSession session = request.getSession();
        String token = (String)request.getSession().getAttribute("token");
        User user = tokenChecker.tokenToUser(token);
        String username = user.getUserName();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("username",username);
            sendManager.sendJSON(response,jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
