package image.show;

import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zjm97 on 2019/4/5.
 */
@WebServlet(name = "getImageAction")
public class getImageAction extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject jsonObject = new JSONObject();
        try{
            List list=getFiles("C:\\Users\\Public\\Pictures\\Sample Pictures");
            jsonObject.put("files",list);

        }catch (JSONException e){
            e.printStackTrace();
        }
        response.setContentType("application/json; charset=UTF-8");
        try{
            response.getWriter().print(jsonObject);
            response.getWriter().flush();
            response.getWriter().close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected ArrayList<String> getFiles(String path){
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        for(int i=0;i<tempList.length;i++){
            if(tempList[i].isFile()){
                String fileName = tempList[i].toString();
                if(fileName.endsWith(".jpg")){
                    fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
                    files.add(fileName);
                }
            }
        }
        return files;
    }
}
