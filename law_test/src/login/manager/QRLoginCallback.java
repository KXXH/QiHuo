package login.manager;

import org.json.JSONException;
import org.json.JSONObject;
import user.manager.User;
import utils.exceptionManager;
import utils.networkOpener;
import utils.sendManager;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * Created by zjm97 on 2019/6/4.
 */
public class QRLoginCallback extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        String code=request.getParameter("code");
        String uuid=request.getPathInfo().substring(1);
        System.out.println("QRLoginCallback:uuid="+uuid);
        if(code==null||code.length()==0){
            sendManager.sendErrorJSONWithMsg(response,"没有授权码!");
            return;
        }
        JSONObject json=new JSONObject();

        try {
            json.put("client_secret","cc46fbbdbd7a070bcc7792b608f545e450cefd72");
            json.put("code",code);
            json.put("client_id","68e32dfb7606dcbc5881");
            String res = jsonPost("https://github.com/login/oauth/access_token",json);
            System.out.println("QRLoginCallback:res="+res);
            JSONObject res_json=new JSONObject(res);
            String token=res_json.getString("access_token");
            String ans=networkOpener.getResponse("https://api.github.com/user?access_token="+token,"utf-8");
            System.out.println("QRLoginCallback:ans="+ans);
            JSONObject user_data=new JSONObject(ans);
            HttpSession session=request.getSession();
            String user_id=user_data.getString("id");
            String login=user_data.getString("login");
            session.setAttribute("user_wechat_id",user_id);
            session.setAttribute("user_wechat_token",token);
            response.sendRedirect("/XM10/login?wechat_id="+user_id+"&wechat_name="+login+"&uuid="+uuid);
            System.out.println("QRLoginCallback:已转发!");
        } catch (JSONException e) {
            e.printStackTrace();
            exceptionManager.logException(e,this);
        }

    }

    /**
     * 发送HttpPost请求
     *
     * @param strURL
     *            服务地址
     * @param params
     *
     * @return 成功:返回json字符串<br/>
     */
    public static String jsonPost(String strURL, JSONObject params) {
        try {
            URL url = new URL(strURL);// 创建连接
            StringBuffer sb=new StringBuffer();
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
            connection.setRequestProperty("Content-Type", "application/json"); // 设置发送数据的格式
            connection.connect();
            OutputStream out = new DataOutputStream(connection.getOutputStream()); // utf-8编码
            out.write((params.toString()).getBytes());
            System.out.println("jsonPost:params="+params.toString());
            out.flush();
            out.close();

            int code = connection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == connection.getResponseCode()){
                System.out.println("连接成功");
                // 请求返回的数据
                InputStream in1 = connection.getInputStream();
                try {
                    String readLine=new String();
                    BufferedReader responseReader=new BufferedReader(new InputStreamReader(in1,"UTF-8"));
                    while((readLine=responseReader.readLine())!=null){
                        sb.append(readLine).append("\n");
                    }
                    responseReader.close();
                    System.out.println(sb.toString());
                    return sb.toString();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "error"; // 自定义错误信息
    }
}
