package utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Created by zjm97 on 2019/5/16.
 */
public class tokenExtractor {
    /**
     * token提取器，向下兼容各种方式的token传递方式，并统一修正至最新标准（session）传递。
     *优先级：
     *1.请求中显式传送"tocken"参数
     *2.请求中显式传送"token"参数
     *3.请求session中隐式传送"token"参数
     *4.请求cookie中隐式传送"token"参数
     *其中，1和2会被修正到3的状态
     * @param request
     * @return 提取得到的token,注意仍然有提取不到token的可能性，需要加以处理
     */
    public static String extractToken(HttpServletRequest request){
        String token;
        token=request.getParameter("tocken");

        if(token==null||token.length()==0|| Objects.equals(token, "false")){
            System.out.println("没有找到显式传递的tocken!");
            token=request.getParameter("token");
        }else{

            request.getSession().setAttribute("token",token);
        }
        if(token==null||token.length()==0|| Objects.equals(token, "false")){
            System.out.println("没有找到显式传递的token!");
            token=(String)request.getSession(true).getAttribute("token");
        }else{
            request.getSession(true).setAttribute("token",token);
        }
        if(token==null||token.length()==0|| Objects.equals(token, "false")){
            System.out.println("没有找到session传递的token!");
            Cookie cookie=cookieManager.getCookieByName(request,"token");
            if(cookie!=null){
                token=cookie.getValue();
                request.getSession().setAttribute("token",token);
            }
        }
        return token;
    }
}
