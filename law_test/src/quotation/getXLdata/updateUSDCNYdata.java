package quotation.getXLdata;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.dbOpener;
import utils.networkOpener;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/5/25.
 */
public class updateUSDCNYdata {
    public static void main(String args[]){
        updateThread T = new updateThread("Thread-updateUSDCNYdata");
        T.start();
    }
}

class updateThread extends Thread {
    private Thread t;
    private String threadName;

    updateThread(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }

    public void run() {
        while(true) {
            Calendar cal = Calendar.getInstance();

            //用Calendar类提供的方法获取年、月、日、时、分、秒
            int year = cal.get(Calendar.YEAR);   //年
            int month = cal.get(Calendar.MONTH) + 1;  //月  默认是从0开始  即1月获取到的是0
            int day = cal.get(Calendar.DAY_OF_MONTH);  //日，即一个月中的第几天
            int hour = cal.get(Calendar.HOUR_OF_DAY);  //小时
            int minute = cal.get(Calendar.MINUTE);   //分
            int second = cal.get(Calendar.SECOND);  //秒

            //拼接成字符串输出
            String date = year + "-" + month + "-" + day + " " + hour + ":" + minute + ":" + second;
            System.out.println("当前时间是---->" + date);

            int i = cal.get(Calendar.DAY_OF_WEEK);
            //如果不是周末，注意要第二天才能取到前一天的数据
            if (i != 1 && i != 2) {
                //09:00之前都是停盘的
                if (hour < 9) {
                    String url = "http://hq.sinajs.cn/list=fx_susdcny";
                    String result;
                    result = networkOpener.getResponse(url, "GBK").split("\"")[1];
                    String Date = result.split(",")[17];
                    double close = Double.parseDouble(result.split(",")[2]);
                    double open = Double.parseDouble(result.split(",")[5]);
                    double low = Double.parseDouble(result.split(",")[7]);
                    double high = Double.parseDouble(result.split(",")[6]);

                    try {
                        Connection conn = dbOpener.getDB();
                        String sql = "INSERT IGNORE INTO tbl_USDCNY (date,open,high,low,close) VALUES(?,?,?,?,?)";
                        PreparedStatement ptmt = conn.prepareStatement(sql);
                        ptmt = conn.prepareStatement(sql);
                        ptmt.setString(1, Date);
                        ptmt.setDouble(2, open);
                        ptmt.setDouble(3, high);
                        ptmt.setDouble(4, low);
                        ptmt.setDouble(5, close);
                        ptmt.execute();
                        conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println(threadName + " 已退出");
            try {
                Thread.sleep(8*60*60*1000);
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
