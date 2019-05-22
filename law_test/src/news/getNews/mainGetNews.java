package news.getNews;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.dbOpener;
/**
 * Created by 11913 on 2019/5/22.
 */
public class mainGetNews {
    public static void main(String args[]){
        GetNewsThread T = new GetNewsThread("Thread-1");
        T.start();
    }
}

class GetNewsThread extends Thread {
    private Thread t;
    private String threadName;

    GetNewsThread(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        while(true){
            System.out.println("Running " + threadName);
            String response = "";
            String param = "key=133bd0946b87b54855e18fae608c9fc1&type=caijing";
            String url = "http://v.juhe.cn/toutiao/index";
            String urlNameString = url + "?" + param;
            BufferedReader in = null;
            try {
                URL realUrl = new URL(urlNameString);
                URLConnection conn = realUrl.openConnection();
                conn.setRequestProperty("accept", "*/*");
                conn.setRequestProperty("connection", "Keep-Alive");
                conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                conn.setRequestProperty("Content-Type", "application/json");
                // 发送POST请求必须设置如下两行
                // 建立实际的连接
                conn.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = conn.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
                }

                in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line;
                while ((line = in.readLine()) != null) {
                    //line = new String(line.getBytes("iso8859-1"), "utf-8");
                    response += line;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println(response);
            JSONObject rootObject, jsonObject;
            JSONArray jsonArray = null;
            try {
                rootObject = new JSONObject(response);
                jsonObject = new JSONObject(rootObject.getString("result"));
                jsonArray = new JSONArray(jsonObject.getString("data"));
                //System.out.println(jsonArray.getJSONObject(0).getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //写入数据库
            try {
                Connection conn = dbOpener.getDB();
                String sql = "INSERT IGNORE INTO tbl_news (title,date, author_name,url,thumbnail_pic_s) VALUES(?,?,?,?,?)";
                PreparedStatement ptmt = conn.prepareStatement(sql);
                ptmt = conn.prepareStatement(sql);
                for (int i = 0; i < jsonArray.length(); i++) {
                    ptmt.setString(1, jsonArray.getJSONObject(i).getString("title"));
                    ptmt.setString(2, jsonArray.getJSONObject(i).getString("date"));
                    ptmt.setString(3, jsonArray.getJSONObject(i).getString("author_name"));
                    ptmt.setString(4, jsonArray.getJSONObject(i).getString("url"));
                    ptmt.setString(5, jsonArray.getJSONObject(i).getString("thumbnail_pic_s"));
                    ptmt.execute();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println(threadName + " 已退出");
            try {
                Thread.sleep(15*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
