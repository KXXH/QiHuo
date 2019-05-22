package news.getNews;

/**
 * Created by 11913 on 2019/5/22.
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import news.getNews.mainGetNews;
public class autoRun implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("正在尝试获取新闻");
        mainGetNews.main(null);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("tomcat已关闭");
    }
}
