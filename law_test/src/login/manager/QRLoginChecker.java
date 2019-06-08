package login.manager;

import org.json.JSONException;
import org.json.JSONObject;
import utils.sendManager;
import utils.tokenGenerator;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/6/7.
 */
public class QRLoginChecker extends javax.servlet.http.HttpServlet {
    protected static HashMap<String,String> QRStatus;
    public static void setQRStatus(String uuid,String status){
        try{
            QRStatus.put(uuid,status);
        }catch(NullPointerException e){
            QRStatus=new HashMap<String,String>();
            QRStatus.put(uuid,status);
        }
    }
    public static String getQRStatus(String uuid){
        return QRStatus.get(uuid);
    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try{
            String uuid=request.getParameter("uuid");
            String status=null;
            try{
                 status=QRStatus.get(uuid);
            }catch(NullPointerException e){
                ;
            }
            if(status==null){
                JSONObject json=new JSONObject();
                json.put("status","unscaned");
                sendManager.sendJSON(response,json);
            }else if(Objects.equals(status, "scaned")){
                JSONObject json=new JSONObject();
                json.put("status","scaned");
                sendManager.sendJSON(response,json);
            }else{
                JSONObject json=new JSONObject();
                json.put("status","ok");
                HttpSession session=request.getSession();
                session.setAttribute("token", status);
                sendManager.sendJSON(response,json);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        JSONObject json=new JSONObject();
        try {
            json.put("username",(String)request.getSession().getAttribute("user_wechat_name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        sendManager.sendJSON(response,json);
    }
}
