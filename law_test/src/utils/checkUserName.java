package utils;


import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 11913 on 2019/5/30.
 */
public class checkUserName {
    public static boolean RegexName(String username){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_\\u4e00-\\u9fa5]+$");
        Matcher matcher = pattern.matcher(username);
        if(matcher.find()){
            username = username.toLowerCase();
            if(username.contains("猪")||username.contains("狗")||username.contains("习近平")||username.contains("江泽民")||username.contains("彭丽媛")||username.contains("妈")||username.contains("爸")||username.contains("fuck"))
                return false;
            return true;
        }
        else
            return false;
    }
}

