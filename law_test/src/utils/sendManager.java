package utils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zjm97 on 2019/5/16.
 */
public class sendManager {
    public static HttpServletResponse sendJSON(HttpServletResponse response, JSONObject jsonObject){
        response.setContentType("application/json; charset=UTF-8");
        try {
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
    public static HttpServletResponse sendSimpleErrorJSON(HttpServletResponse response){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status","error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sendJSON(response,jsonObject);
    }
    public static HttpServletResponse sendSimpleOKJSON(HttpServletResponse response){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status","ok");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sendJSON(response,jsonObject);
    }
}
