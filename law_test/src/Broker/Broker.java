package Broker;

import utils.SensitiveWordChecked;
import utils.dbOpener;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by 11913 on 2019/6/6.
 */
@ServerEndpoint("/Broker/{userId}")
public class Broker {
    private final static int MAX_SIZE = 20;
    private static ArrayBlockingQueue<String> messageQueue = new ArrayBlockingQueue<String>(MAX_SIZE);

    private static int onlineCount = 0;
    private static Map<String,Broker> users = Collections.synchronizedMap(new HashMap());
    private Session session;
    private String username;

    @OnOpen
    public void open(Session session, @PathParam("userId") String username){
        this.session = session;
        this.username = username;
        users.put(username,this);
        addOnlineCount();
        try{
            this.session.getBasicRemote().sendText("连接成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClose
    public void close(){
        users.remove(this.username);
        subOnlineCount();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    //    给特定人员发送消息
    public static int sendMessageToSomeBody(String username, String message) throws IOException {
        if(users.get(username)==null){
            return 0;
        }
        //System.out.println(username+":"+message);
        users.get(username).session.getBasicRemote().sendText(message);
        return 1;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        Broker.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        Broker.onlineCount--;
    }



    //生产消息
    public static void produce(String msg) throws IOException {
        System.out.println("[消息中间件]===================================");
        if(messageQueue.offer(msg)){
            System.out.println("[消息中间件]收到消息：" + msg + ",暂存消息：" + messageQueue.size() + "条。");
            if(sendMessageToSomeBody(msg.split(":")[0],msg.split(":")[1])==1){
                System.out.println("发送成功！！！");
                messageQueue.poll();
            }
            else{
                System.out.println("发送失败！！！已写入数据库！！！");
                messageQueue.poll();
                try{
                    Connection conn = dbOpener.getDB();
                    String sql = "INSERT IGNORE INTO tbl_notifications (message,datetime) VALUES(?,CURRENT_TIMESTAMP())";
                    PreparedStatement ptmt = conn.prepareStatement(sql);
                    ptmt = conn.prepareStatement(sql);
                    ptmt.setString(1, msg);
                    ptmt.execute();
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }else {
            System.out.println("[消息中间件]消息已满！");
        }
        System.out.println("[消息中间件]===================================");
    }

    //保持连接
    public static void connect() throws IOException{
        System.out.println("已连接上服务器");
    }

    public static void num(){
        System.out.println("当前连接人数是"+onlineCount);
    }
}
