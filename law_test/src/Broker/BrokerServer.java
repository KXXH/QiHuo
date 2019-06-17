package Broker;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 11913 on 2019/6/6.
 */
public class BrokerServer implements Runnable {
    public static int SERVICE_PORT = 9999;
    private final Socket socket;

    private static int onlineCount = 0;
    private static Map<String,BrokerServer> users = Collections.synchronizedMap(new HashMap());
    private Session session;
    private String username;

    public BrokerServer(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            while(socket.getInputStream().available() != 0){
                String str = in.readLine();
                if(str == null){
                    continue;
                }
                System.out.println("[服务器]收到消息：" + str);
                if("连接".equals(str)){
                    Broker.connect();
                }else{
                    Broker.produce(str);
                }
                Broker.num();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws IOException{
        ServerSocket server = new ServerSocket(SERVICE_PORT);
        while(true){
            BrokerServer brokerServer = new BrokerServer(server.accept());
            System.out.println("Server启动");
            new Thread(brokerServer).start();

        }
    }
}
