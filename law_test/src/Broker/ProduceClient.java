package Broker;

/**
 * Created by 11913 on 2019/6/6.
 */
public class ProduceClient {
    public static void main(String[] args) throws Exception{
        MqClient mqClient = new MqClient();
        System.out.println("MqClient启动");
        mqClient.produce("Test");
    }
    public static void send(String msg) throws Exception {
        MqClient mqClient = new MqClient();
        mqClient.produce(msg);
    }
    public static void start() throws Exception {
        MqClient mqClient = new MqClient();
        mqClient.connect();
    }
}
