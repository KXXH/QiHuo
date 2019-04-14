package file.upload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by zjm97 on 2019/4/4.
 */
@WebServlet(name = "uploadAction")
@MultipartConfig
public class uploadAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("执行了POST");
        HttpSession session = request.getSession();
        List<String> list = (List<String>)session.getAttribute("files");
        List<String> realName = (List<String>)session.getAttribute("realNames");
        if(list==null){
            list = new ArrayList<String>();
        }
        if(realName==null){
            realName = new ArrayList<String>();
        }
        Part part = request.getPart("file");
        String name = part.getHeader("content-disposition");
        System.out.println("文件名是:"+name);
        String root = request.getServletContext().getRealPath("/fileUpload");
        //String root = "/fileUpload";
        String str = name.substring(name.lastIndexOf("."),name.length()-1);
        String fname=UUID.randomUUID().toString()+str;
        String filename = root+"\\"+ fname;
        //String filename = root+"/"+ UUID.randomUUID().toString()+str;
        System.out.println("路径名是:"+filename);
        part.write(filename);
        list.add(fname);
        session.setAttribute("files",list);
        request.setAttribute("info","上传文件成功");
        request.getRequestDispatcher("/fileDemo.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
