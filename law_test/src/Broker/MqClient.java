package Broker;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by 11913 on 2019/6/6.
 */
public class MqClient {

    public static void produce(String message) throws Exception{
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println(message);
        out.flush();
    }
    public static void connect() throws Exception{
        Socket socket = new Socket(InetAddress.getLocalHost(), BrokerServer.SERVICE_PORT);
        PrintWriter out = new PrintWriter(socket.getOutputStream());
        out.println("连接");
        out.flush();
    }
}
