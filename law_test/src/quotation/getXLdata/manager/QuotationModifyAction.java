package quotation.getXLdata.manager;

import org.json.JSONObject;
import permission.manager.permissionChecker;
import utils.dbOpener;
import utils.sendManager;
import javax.servlet.annotation.WebServlet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by 11913 on 2019/6/6.
 */
@WebServlet(name = "QuotationModifyAction")
public class QuotationModifyAction extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        if (!permissionChecker.checkPermissionAndResponse(request, response, this)) return;

        String id = request.getParameter("id");
        String Code = request.getParameter("Code");
        String Name = request.getParameter("Name");
        Double Quotation = Double.valueOf(request.getParameter("Quotation"));
        Double RiseOrFall = Double.valueOf(request.getParameter("RiseOrFall"));
        Double ROFper = Double.valueOf(request.getParameter("ROFper"));

        if(Quotation <=0 || Math.abs(RiseOrFall) > Quotation*0.1 || ROFper > 10){
            sendManager.sendSimpleErrorJSON(response);
            return;
        }
        try {
            Connection conn = dbOpener.getDB();
            String sql = "UPDATE tbl_quotation SET Quotation=?, RiseOrFall=?, ROFper=? WHERE id=?";
            PreparedStatement ptmt = conn.prepareStatement(sql);
            ptmt = conn.prepareStatement(sql);
            ptmt.setDouble(1, Quotation);
            ptmt.setDouble(2, RiseOrFall);
            ptmt.setDouble(3, ROFper);
            ptmt.setString(4, id);
            ptmt.execute();
            conn.close();
            JSONObject jsonObject = new JSONObject();
        } catch (SQLException e) {
            e.printStackTrace();
            sendManager.sendSimpleErrorJSON(response);
        }
        sendManager.sendSimpleOKJSON(response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
