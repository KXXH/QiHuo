package utils;
import org.apache.commons.codec.digest.DigestUtils;
/**
 * Created by zjm97 on 2019/6/1.
 */
public class MD5 {
    public static final String Key="qihuoqihuoqihuo";
    public static String md5(String text){
        return DigestUtils.md2Hex(text+Key);
    }
    public static  boolean verify(String text,String md5){
        return md5(text).equals(md5);
    }
}
