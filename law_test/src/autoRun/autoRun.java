package autoRun;

/**
 * Created by 11913 on 2019/5/22.
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import news.getNews.mainGetNews;
import quotation.getXLdata.getEconomicData;
import quotation.getXLdata.moveDataToDB;
import quotation.getXLdata.updateUSDCNYdata;
import utils.SensitiveWordChecked;
import utils.SensitiveWordInit;
import utils.checkUserName;
import Broker.BrokerServer;
import Broker.ProduceClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class autoRun implements ServletContextListener{
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {

        //System.out.println("正在尝试获取新闻");

        mainGetNews.main(null);
        try {
            getEconomicData.main(null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        updateUSDCNYdata.main(null);

        SensitiveWordInit sensitiveWordInit = new SensitiveWordInit();
        HashMap<String,String> map = (HashMap) sensitiveWordInit.initKeyWord();

        System.out.println(SensitiveWordInit.sensitiveWordMap);

        ServerThread T = new ServerThread("Thread-server");
        T.start();

    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("tomcat已关闭");
    }
}

class ServerThread extends Thread {
    private Thread t;
    private String threadName;

    ServerThread(String name) {
        threadName = name;
        System.out.println("Creating " + threadName);
    }
    public void run(){
        try {
            BrokerServer.main(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void start() {
        System.out.println("Starting " + threadName);
        if (t == null) {
            t = new Thread(this, threadName);
            t.start();
        }
    }
}
