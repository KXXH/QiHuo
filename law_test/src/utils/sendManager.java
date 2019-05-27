package utils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zjm97 on 2019/5/16.
 * 响应发送工具类，可以发送简单的JSON信息，也可以发送简单的格式化错误信息和成功信息
 */
public class sendManager {
    /**
     *发送JSON信息的工具方法
     * @param response
     * @param jsonObject 响应的JSON
     * @return 将传入的response返回
     */
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

    /**
     *发送简单错误信息的工具方法，其内容为:{"status":"error"}
     * @param response
     * @return 将传入的response返回
     */
    public static HttpServletResponse sendSimpleErrorJSON(HttpServletResponse response){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status","error");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sendJSON(response,jsonObject);
    }

    public static HttpServletResponse sendErrorJSONWithMsg(HttpServletResponse response,String msg){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status","error");
            jsonObject.put("error",msg);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sendJSON(response,jsonObject);
    }

    public static HttpServletResponse sendErrorJSONWithMsgAndCode(HttpServletResponse response,String msg,int code){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status","error");
            jsonObject.put("error",msg);
            jsonObject.put("code",code);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sendJSON(response,jsonObject);
    }

    /**
     * 发送简单成功信息的工具方法，其内容为:{"status","ok"}
     * @param response
     * @return 将传入的response返回
     */
    public static HttpServletResponse sendSimpleOKJSON(HttpServletResponse response){
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("status","ok");
        } catch (JSONException e) {
            exceptionManager.logException(e,Thread.currentThread().getStackTrace()[1].getClassName());
            e.printStackTrace();
        }
        return sendJSON(response,jsonObject);
    }

    public static HttpServletResponse sendDefaultPermissionError(HttpServletResponse response){
        return sendManager.sendErrorJSONWithMsgAndCode(response,"权限不足!",123);
    }
}
