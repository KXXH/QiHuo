package quotation.getXLdata;

import com.sun.org.apache.xerces.internal.impl.xpath.regex.Match;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import utils.dbOpener;
import utils.networkOpener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 11913 on 2019/5/22.
 */
public class getEconomicData {
    static String[] hl = {"fx_susdcny","fx_seurcny","fx_scnyjpy","fx_sgbpcny","fx_susdcnh","fx_skrwcny","fx_shkdcny","fx_scnytwd"};
    static String[] qh = {"hf_GC","hf_CL","hf_OIL","hf_SI","hf_S","hf_C","hf_XAU"};
    static String[] mg = {"gb_dji","gb_ixic","gb_inx","gb_amzn"};
    static String[] og = {"b_NKY","b_DAX","b_CAC","b_UKX"};
    static String[] gg = {"hkHSI"};
    static String[] zg = {"s_sh000001","s_sh000300","s_sz399001","s_sz399006"};
    static String[] mz = {"DINIW"};
    static String[] xm = {"btc_btcbitstamp","btc_btcethusd","btc_btcltcusd"};
    static String Glist[][] = {zg,gg,og,mg,qh,hl,mz,xm};
    public static void main(String args[]) throws InterruptedException {

        for(int i = 0; i < 8; i++){
            GetDataThread t = new GetDataThread(i,"Thread-"+i);
            t.start();
            Thread.sleep(2000);
        }
    }
}
class Quotation{
    private String Code;
    private String Name;
    private double quotation;
    private double RiseOrFall;
    private double ROFper;
    private int kind;

    void setCode(String code){
        this.Code = code;
    }
    void setName(String name){
        this.Name = name;
    }
    void setQuotation(double quotation){
        this.quotation = quotation;
    }
    void setRiseOrFall(double riseOrFall){
        this.RiseOrFall = riseOrFall;
    }
    void setROFper(double roFper){
        this.ROFper = roFper;
    }
    void setKind(int kind){
        this.kind = kind;
    }
    String getCode(){
        return Code;
    }
    String getName(){
        return Name;
    }
    double getQuotation(){
        return quotation;
    }
    double getRiseOrFall(){
        return RiseOrFall;
    }
    double getROFper(){
        return ROFper;
    }
    int getKind(){
        return kind;
    }
}
class GetDataThread extends Thread {
    private Thread t;
    private String ThreadName;
    private int QueryKind;

    GetDataThread(int kind,String name) {
        QueryKind = kind;
        ThreadName = name;
        System.out.println("Creating" + ThreadName);
    }
    public void run() {
        while(true){
            System.out.println("Running" + ThreadName);
            String response = "";
            String url = "http://hq.sinajs.cn/list=";
            int len = getEconomicData.Glist[QueryKind].length;
            for(int i = 0; i < len ;i++){
                url += getEconomicData.Glist[QueryKind][i];
                url += ",";
            }
            response = networkOpener.getResponse(url,"GBK");
            //System.out.println(response);

            //处理数据得到内容,并写入数据库
            Quotation[] q = new Quotation[len];
            for(int i = 0; i < len; i++){
                q[i] = new Quotation();
                String result = response.split(";")[i];
                String content = result.split("\"")[1];
                String code = getEconomicData.Glist[QueryKind][i];
                q[i].setCode(code);
                DecimalFormat df=new DecimalFormat("0.0000");
                switch (QueryKind){
                    case 0:
                        q[i].setName(content.split(",")[0]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[1]));
                        q[i].setRiseOrFall(Double.parseDouble(content.split(",")[2]));
                        q[i].setROFper(Double.parseDouble(content.split(",")[3]));
                        break;
                    case 1:
                        q[i].setName(content.split(",")[1]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[6]));
                        q[i].setRiseOrFall(Double.parseDouble(content.split(",")[7]));
                        q[i].setROFper(Double.parseDouble(content.split(",")[8]));
                        break;
                    case 2:
                        q[i].setName(content.split(",")[0]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[1]));
                        q[i].setRiseOrFall(Double.parseDouble(content.split(",")[2]));
                        q[i].setROFper(Double.parseDouble(content.split(",")[3]));
                        break;
                    case 3:
                        q[i].setName(content.split(",")[0]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[1]));
                        q[i].setRiseOrFall(Double.parseDouble(content.split(",")[4]));
                        q[i].setROFper(Double.parseDouble(content.split(",")[2]));
                        break;
                    case 4:
                        q[i].setName(content.split(",")[13]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[0]));
                        q[i].setROFper(Double.parseDouble(content.split(",")[1]));
                        Double d = Double.parseDouble(content.split(",")[0])-Double.parseDouble(content.split(",")[7]);
                        q[i].setRiseOrFall(Double.parseDouble(df.format(d)));
                        break;
                    case 5:
                        q[i].setName(content.split(",")[9]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[2]));
                        q[i].setROFper(Double.parseDouble(content.split(",")[10]));
                        d = Double.parseDouble(content.split(",")[2])-Double.parseDouble(content.split(",")[3]);
                        q[i].setRiseOrFall(Double.parseDouble(df.format(d)));
                        break;
                    case 6:
                        q[i].setName(content.split(",")[9]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[1]));
                        double d1 = Double.parseDouble(content.split(",")[2])-Double.parseDouble(content.split(",")[3]);
                        double d2 = d1/Double.parseDouble(content.split(",")[3])*100;
                        q[i].setRiseOrFall(Double.parseDouble(df.format(d1)));
                        q[i].setROFper(Double.parseDouble(df.format(d2)));
                        break;
                    case 7:
                        q[i].setName(content.split(",")[9]);
                        q[i].setQuotation(Double.parseDouble(content.split(",")[8]));
                        double d3 = Double.parseDouble(content.split(",")[8])-Double.parseDouble(content.split(",")[3]);
                        double d4 = d3/Double.parseDouble(content.split(",")[3])*100;
                        q[i].setRiseOrFall(Double.parseDouble(df.format(d3)));
                        q[i].setROFper(Double.parseDouble(df.format(d4)));
                        break;
                }
                q[i].setKind(QueryKind);
            }
            try {
                Connection conn = dbOpener.getDB();
                String sql = "UPDATE tbl_quotation SET Quotation=?, RiseOrFall=?, ROFper=? WHERE Code=?";
                for(int i = 0; i < len; i++){
                    PreparedStatement ptmt = conn.prepareStatement(sql);
                    ptmt = conn.prepareStatement(sql);
                    ptmt.setDouble(1, q[i].getQuotation());
                    ptmt.setDouble(2, q[i].getRiseOrFall());
                    ptmt.setDouble(3, q[i].getROFper());
                    ptmt.setString(4, q[i].getCode());
                    ptmt.execute();
                }
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            System.out.println(ThreadName + "已退出");
            try {
                Thread.sleep(5*60*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void start() {
        System.out.println("Starting " + ThreadName);
        if (t == null) {
            t = new Thread(this, ThreadName);
            t.start();
        }
    }
}


