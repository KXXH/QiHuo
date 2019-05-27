package permission.manager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import user.manager.User;
import utils.*;

/**
 * Created by zjm97 on 2019/5/23.
 */
public class editUserPermissionAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        User opUser= tokenChecker.tokenToUser(tokenExtractor.extractToken(request));
        String str_id=request.getParameter("user_id");
        int id=0;
        User targetUser=null;
        if(str_id!=null){
            id=Integer.parseInt(str_id);
            targetUser=User.findUserById(id);
        }
        System.out.println(id);

        if(str_id==null||targetUser.is_Editable(opUser)){
            JSONObject jsonObject=new JSONObject();
            JSONArray jsonArray=new JSONArray();
            switch(opUser.getRole_id()){
                case "super_admin":
                    jsonArray.put("admin");
                case "admin":
                    jsonArray.put("unchecked");
                    jsonArray.put("normal");
            }
            try {
                jsonObject.put("status","ok");
                jsonObject.put("choice",jsonArray);
            } catch (JSONException e) {
                exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
                e.printStackTrace();
            }
            sendManager.sendJSON(response,jsonObject);

        }else{
            sendManager.sendDefaultPermissionError(response);
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
