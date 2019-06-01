package chat;

/**
 * Created by 11913 on 2019/5/29.
 */
import com.mysql.jdbc.StringUtils;
import utils.SensitiveWordChecked;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ChatTest/{userId}")
public class ChatTest {
    private static int onlineCount = 0;
    private static Map<String,ChatTest> users = Collections.synchronizedMap(new HashMap());
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

    @OnMessage
    public void sendTex(String msg){
        SensitiveWordChecked sensitiveWordChecked = new SensitiveWordChecked();
        //@ALL表示群发，@XXX#message表示私发
        //System.out.println(msg.charAt(msg.length()-1));
        try{
            if(msg.length()==0||msg.charAt(msg.length()-1)=='#')
                return;
            String[] split = msg.split("#");
            if(split.length>1){
                String[] users = split[0].split("@");
                if(users.length<2){return;}
                String firstuser = users[1].trim();
                String txt = sensitiveWordChecked.replaceSensitiveWord(split[1],1,"*");
                if (firstuser==""||"ALL".equals(firstuser.toUpperCase())){
                    String message =username +": "+ txt;
                    sendInfo(message);//群发消息
                }else{//给特定人员发消息
                    for (String user : users) {
                        sendMessageToSomeBody(user.trim(),txt);
                    }
                }
            }else{
                msg = sensitiveWordChecked.replaceSensitiveWord(msg,1,"*");
                sendInfo(username +": "+msg);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }
    //    给特定人员发送消息
    public void sendMessageToSomeBody(String username,String message) throws IOException {
        if(users.get(username)==null){
            return;
        }
        users.get(username).session.getBasicRemote().sendText(this.username+":"+message);
        this.session.getBasicRemote().sendText(this.username+"@"+username+": "+message);
    }

    /**
     * 群发自定义消息
     */
    public void sendInfo(String message) throws IOException {
        for (ChatTest item : users.values()) {
            try {
                item.session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                continue;
            }
        }
    }
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ChatTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ChatTest.onlineCount--;
    }
}
