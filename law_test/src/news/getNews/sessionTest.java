package news.getNews;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpSession;

/**
 * Created by 11913 on 2019/5/15.
 */
@WebServlet(name = "sessionTest")
public class sessionTest extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        //使用request对象的getSession()获取session，如果session不存在则创建一个
        HttpSession session = request.getSession();
        //将数据保存到session中
        session.setAttribute("hsj",123);
        //获取session的Id
        String sessionId = session.getId();
        //判断session是不是新建的
        if(session.isNew()){
            response.getWriter().print("session创建成功，session的id是："+session.getAttribute("hsj"));
        }else{
            response.getWriter().print("服务器已经存在该session了，session的id是："+session.getAttribute("hsj"));
        }
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

    }
}
