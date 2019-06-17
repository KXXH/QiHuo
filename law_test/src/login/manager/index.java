package login.manager;

import permission.manager.permissionChecker;
import user.manager.User;
import utils.tokenChecker;
import utils.tokenExtractor;

/**
 * Created by zjm97 on 2019/6/17.
 */
public class index extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        System.out.println("执行了index");
        if(!permissionChecker.checkPermissionAndResponse(request,response,this)) return;
        User user= tokenChecker.tokenToUser(tokenExtractor.extractToken(request));
        System.out.println("index:用户是"+user.getRole_id());
        switch(user.getRole_id()){
            case "super_admin":
                System.out.println("index:用户是超级管理员");
                request.getRequestDispatcher("superadminIndex.html").forward(request, response);
                break;
            case "admin":
            case "normal":
                System.out.println("index:用户是其他");
                request.getRequestDispatcher("index.html").forward(request, response);
                break;
        }
    }
}
