package news.getNews;

import org.json.JSONException;
import org.json.JSONObject;
import permission.manager.permissionChecker;
import user.manager.User;
import utils.dbOpener;
import utils.sendManager;
import utils.tokenChecker;
import utils.tokenExtractor;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.sql.Date;
import java.util.*;

/**
 * Created by 11913 on 2019/4/17.
 */
@WebServlet(name = "newsStatisticsAction")
public class newsStatisticsAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;

        /*
        //得到token
        String token = tokenExtractor.extractToken(request);
        //从token得到user实例
        User user= tokenChecker.tokenToUser(token);
        //验证
        if(user==null|| !Objects.equals(user.getRole_id(), "admin")) {
            sendManager.sendSimpleErrorJSON(response);
            return;
        }*/

        String order_1 = request.getParameter("order_1");
        String order_2 = request.getParameter("order_2");
        try{
            Connection conn = dbOpener.getDB();
            Statement statement = conn.createStatement();
            String sql = "SELECT * FROM tbl_news ORDER BY " + order_1 +"," +order_2;
            ResultSet rs = statement.executeQuery(sql);
            JSONObject jsonObject = new JSONObject();
            ArrayList list = new ArrayList();
            Map<Date,String> map = new HashMap<Date,String>();
            while(rs.next()){
                Date date = rs.getDate("date");
                if(map.containsKey(date)){
                    String num = map.get(date);
                    int i =Integer.valueOf(num).intValue();
                    i++;
                    num = String.valueOf(i);
                    map.put(date,num);
                }
                else{
                    map.put(date,"1");
                }
            }
            conn.close();
            Set set=map.keySet();
            Object[] arr=set.toArray();
            Arrays.sort(arr);
            for(Object key:arr) {
                Map temp = new HashMap();
                temp.put("date",key);
                temp.put("value",map.get(key));
                list.add(temp);
            }

            jsonObject.put("aaData",list);
            System.out.println(jsonObject.getString("aaData"));
            response.setContentType("application/json; charset=UTF-8");
            try {
                response.getWriter().print(jsonObject);
                response.getWriter().flush();
                response.getWriter().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        System.out.println("执行完毕已返回");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
