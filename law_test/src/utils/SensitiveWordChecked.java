package utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by 11913 on 2019/6/1.
 */
public class SensitiveWordChecked {
    private Map sensitiveWordMap = SensitiveWordInit.sensitiveWordMap;
    private static int minMatchType = 1;
    private static int maxMatchType = 2;

    //判断文字是否包含敏感字符
    public boolean isContainSensitiveWord(String txt,int matchType){
        boolean flag = false;
        for(int i = 0; i < txt.length(); i++){
            int matchFlag = this.CheckSensitiveWord(txt,i,matchType);
            if(matchFlag > 0)
                flag = true;
        }
        return flag;
    }

    //获取文字中的敏感词
    public Set<String> getSensitiveWord(String txt,int matchType){
        Set<String> sensitiveWordList = new HashSet<String>();
        for(int i = 0; i < txt.length(); i++){
            int length = CheckSensitiveWord(txt,i,matchType);
            //System.out.println("length:"+length);
            if(length > 0){
                sensitiveWordList.add(txt.substring(i,i+length));
                i = i + length - 1;
            }
        }
        return sensitiveWordList;
    }

    //替换敏感字符，默认为*
    public String replaceSensitiveWord(String txt,int matchType,String replaceChar){
        String resultTxt = txt;
        Set<String> set = getSensitiveWord(txt,matchType);
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceStr = null;
        while(iterator.hasNext()){
            word = iterator.next();
            //System.out.println(word);
            replaceStr = getReplaceChars(replaceChar,word.length());
            resultTxt = resultTxt.replaceAll(word,replaceStr);
        }
        return resultTxt;
    }

    //获取替换字符串
    private String getReplaceChars(String replaceChar,int length){
        String resultReplace = replaceChar;
        for(int i = 1; i < length; i++)
            resultReplace += replaceChar;
        return resultReplace;
    }

    public int CheckSensitiveWord(String txt,int beginIndex,int matchType) {
        boolean flag = false;
        int matchFlag = 0;
        char word = 0;
        Map nowMap = sensitiveWordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            //System.out.println(word);
            nowMap = (Map) nowMap.get(word);
            if (nowMap != null) {
                matchFlag++;
                if ("1".equals(nowMap.get("isEnd"))) {
                    flag = true;
                    if (SensitiveWordChecked.minMatchType == matchType) {
                        break;
                    }
                }
            }
            else
                break;
        }
        if(matchFlag < 2||!flag)
            matchFlag = 0;
        return matchFlag;
    }
}
