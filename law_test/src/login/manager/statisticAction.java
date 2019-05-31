package login.manager;

import org.apache.commons.collections.FastHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/5/31.
 */
public class statisticAction extends javax.servlet.http.HttpServlet {


    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        String strDayCount=request.getParameter("dayCount");
        int dayCount=7;

        if(strDayCount!=null&&strDayCount.length()>0){
            dayCount=Integer.parseInt(strDayCount);
        }
        String sql="SELECT SUBSTRING_INDEX(SUBSTRING_INDEX(location,' ',2),' ',-1) AS province,COUNT(*) AS count FROM tbl_logininfo WHERE DATEDIFF(NOW(),time)<? GROUP BY SUBSTRING_INDEX(SUBSTRING_INDEX(location,' ',2),' ',-1)";
        int min=-1,max=-1;
        try {
            Connection conn= dbOpener.getDB();
            PreparedStatement ptmt=conn.prepareStatement(sql);
            ptmt.setInt(1,dayCount);
            ResultSet rs=ptmt.executeQuery();
            JSONArray array=new JSONArray();
            while(rs.next()){
                JSONObject j=new JSONObject();
                int count=rs.getInt("count");
                String province=locaionManager.proviceNameToPinYin(rs.getString("province"));
                if((min<0||max<0)&&province!=null&&province.length()>0&& !Objects.equals(province, "unknown")){
                    max=min=count;
                }
                if((count>max)&&province!=null&&province.length()>0&& !Objects.equals(province, "unknown")){
                    max=count;
                }
                if((count<min)&&province!=null&&province.length()>0&& !Objects.equals(province, "unknown")){
                    min=count;
                }
                j.put("id",province);
                j.put("value",count);
                array.put(j);
            }
            JSONObject json=new JSONObject();
            json.put("locationData",array);
            json.put("min",min);
            json.put("max",max);
            json.put("status","ok");
            sendManager.sendJSON(response,json);
        } catch (SQLException | JSONException e) {
            exceptionManager.logException(e,this);
            e.printStackTrace();
        }

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
