package user.manager;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by zjm97 on 2019/4/17.
 */



public class statisticAction extends javax.servlet.http.HttpServlet {
    class DataComp implements Comparator<JSONObject>{
        @Override
        public int compare(JSONObject o1, JSONObject o2) {
            try {
                return o1.getString("date").compareTo(o2.getString("date"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return 0;
        }
    }
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("执行了POST-stat");
        HashMap map=new HashMap<Date,Integer>();
        String token=request.getParameter("token");
        String user_role=tokenChecker.checkToken(token);
        System.out.println("user_role="+user_role);
        List jsonList = new ArrayList();
        if(!Objects.equals(user_role, "admin")){
            try{
                JSONObject json = new JSONObject();
                json.put("status","error");
                response.setContentType("application/json; charset=UTF-8");
                try{
                    response.getWriter().print(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                return;
            }catch(JSONException e){
                e.printStackTrace();
            }
        }
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb?user=root&password=123456&useUnicode=true&characterEncoding=UTF-8");
            String sql = "SELECT * FROM tbl_userinfo ORDER BY CreateAt";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ResultSet rs = ptmt.executeQuery();
            while(rs.next()){
                Date date=rs.getDate("CreateAt");
                System.out.println("检测到一条记录!");
                if(map.containsKey(date)){
                    map.put(date,(int)map.get(date)+1);
                }else{
                    map.put(date,1);
                }
            }
            Iterator iter = map.entrySet().iterator();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while(iter.hasNext()){
                JSONObject json=new JSONObject();
                Map.Entry entry = (Map.Entry) iter.next();
                json.put("date",sdf.format((Date)entry.getKey()));
                json.put("value",(int)entry.getValue());
                jsonList.add(json);
            }
            jsonList.sort(new DataComp());
            try{
                JSONObject json = new JSONObject();
                json.put("status","ok");
                json.put("data",jsonList);
                response.setContentType("application/json; charset=UTF-8");
                try{
                    response.getWriter().print(json);
                    response.getWriter().flush();
                    response.getWriter().close();
                }
                catch(IOException e){
                    e.printStackTrace();
                }
                return;
            }catch(JSONException e){
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
