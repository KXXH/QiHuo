package mailService;
import java.security.GeneralSecurityException;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import com.sun.mail.util.MailSSLSocketFactory;
import utils.exceptionManager;

/**
 * Created by zjm97 on 2019/5/15.
 */
public class sendMail {

    private static void sendQQEmail(String targetEmail,String subject,String text) throws GeneralSecurityException {
        // 收件人电子邮箱
        String to = targetEmail;

        // 发件人电子邮箱
        String from = "283028294@qq.com";

        // 指定发送邮件的主机为 smtp.qq.com
        String host = "smtp.qq.com";  //QQ 邮件服务器

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        properties.put("mail.smtp.auth", "true");
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.ssl.socketFactory", sf);
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties,new Authenticator(){
            public PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication("283028294@qq.com", "gbjgrugkovpfcafh"); //发件人邮件用户名、密码
            }
        });

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject(subject);

            // 设置消息体
            message.setText(text);

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            exceptionManager.logException(mex,Thread.currentThread().getStackTrace()[1].getClassName());
            mex.printStackTrace();
        }
    }

    public static void sendForgetPasswdURL(String targetEmail,String url) throws GeneralSecurityException {
        sendQQEmail(targetEmail,"[期货交易平台]密码重置邮件","请点击下面链接重置密码,如非本人操作请忽略!\n"+url);
    }

    public static void sendEnableAccountEmail(String targetEmail,String url) throws GeneralSecurityException {
        sendQQEmail(targetEmail,"[期货交易平台]帐号激活邮件","请点击下面链接激活帐号,如非本人操作请忽略!\n"+url);
    }

    public static void sendOffsiteLoginEmail(String targetEmail,String location,String ip,String username) throws GeneralSecurityException {
        sendQQEmail(targetEmail,"[期货交易平台]异地登录提醒","您的帐户"+username+"最近在"+location+"(IP:"+ip+")异地登录,如是您本人操作,请忽略此邮件,否则,您的帐户密码可能已经泄露,请立即重置您的密码!");
    }

}
