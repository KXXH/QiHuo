package trade.menu;

import org.json.JSONException;
import org.json.JSONObject;
import user.manager.User;

import java.io.IOException;
import java.sql.*;
import java.util.*;

import utils.dbOpener;
import utils.tokenChecker;
import utils.sendManager;
import utils.*;
/**
 * Created by zjm97 on 2019/3/27.
 */
public class queryAction extends javax.servlet.http.HttpServlet{
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        System.out.println("执行了post");
        String token=tokenExtractor.extractToken(request);
        System.out.println("Token at queryAction is "+token);
        List<List<java.io.Serializable>> jsonList = new ArrayList<>();
        User user= tokenChecker.checkTokenAndRedirect(request,response);
        if(user==null){
            return;
        }
        String userType=user.getRole_id();
        String sql="SELECT * FROM tbl_law_menu WHERE role_id=?";
        Connection conn = null;
        try {
            conn = dbOpener.getDB();
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt.setString(1,userType);
            ResultSet rs=ptmt.executeQuery();
            while(rs.next()){
                List<java.io.Serializable> list = new ArrayList<java.io.Serializable>();
                list.add(rs.getString("role_id"));
                list.add(rs.getString("href"));
                list.add(rs.getString("caption"));
                list.add(rs.getInt("parent_id"));
                jsonList.add(list);
            }
            conn.close();
        } catch (SQLException e1) {
            exceptionManager.logException(e1,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e1.printStackTrace();
        }
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("aaData",jsonList);
        } catch (JSONException e) {
            exceptionManager.logException(e,this,tokenChecker.tokenToUser(tokenExtractor.extractToken(request)));
            e.printStackTrace();
        }
        sendManager.sendJSON(response,jsonObject);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }


}
