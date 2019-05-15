package user.manager;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Date;

/**
 * Created by zjm97 on 2019/5/15.
 */
public class tokenGenerator {
    public static String generateToken(String userName){
        Date date = new Date();
        try {
            return Base64.getEncoder().encodeToString((userName+String.valueOf(date.getTime())).getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "error";
        }
    }
}
