package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created by 11913 on 2019/6/1.
 */

public class SensitiveWordInit {
    private String ENCODING = "utf-8";
    public static HashMap sensitiveWordMap;
    public static int flag = 0;
    public SensitiveWordInit(){
        super();
    }

    public Map initKeyWord(){
        if(flag == 0){
            System.out.println("正在获取！！！");
            try{
                //读取敏感词库
                Set<String> keyWordSet = readSensitiveWordFile();
                //将敏感词库加入到HashMap中
                addSensitiveWordToHashMap(keyWordSet);
            }catch (Exception e){
                e.printStackTrace();
            }
            flag = 1;
        }
        else{
            System.out.println("已经获取到了！！！");
        }
        return sensitiveWordMap;
    }
    private void addSensitiveWordToHashMap(Set<String> keyWordSet){
        sensitiveWordMap = new HashMap(keyWordSet.size());
        String key = null;
        Map nowMap = null;
        Map<String,String> newWordMap = null;
        //迭代keyWordSet
        Iterator<String> iterator = keyWordSet.iterator();
        while(iterator.hasNext()){
            key = iterator.next();
            nowMap = sensitiveWordMap;
            for(int i = 0; i < key.length(); i++){
                char keyChar = key.charAt(i);
                Object wordMap = nowMap.get(keyChar);

                if(wordMap != null){
                    nowMap = (Map) wordMap;
                }
                else{
                    newWordMap = new HashMap<String, String>();
                    newWordMap.put("isEnd","0");
                    nowMap.put(keyChar,newWordMap);
                    nowMap = newWordMap;
                }
                if(i == key.length() - 1){
                    nowMap.put("isEnd","1");
                }
            }
        }
    }
    @SuppressWarnings("resource")
    private Set<String> readSensitiveWordFile() throws Exception{
        Set<String> set = null;
        //String path = this.getClass().getClassLoader().getResource("").getPath();
        String path = "C:\\Program Files (x86)\\Apache Software Foundation\\Tomcat 8.0\\webapps\\XM10\\WEB-INF\\classes\\";
        path = path+"SensitiveWord.txt";
        System.out.println(path);
        File file = new File(path);
        InputStreamReader read = new InputStreamReader(new FileInputStream(file),ENCODING);
        try{
            if(file.isFile()&&file.exists()){
                set = new HashSet<String>();
                BufferedReader bufferedReader = new BufferedReader(read);
                String txt = null;
                while((txt = bufferedReader.readLine())!=null){
                    set.add(txt);
                }
            }
            else{
                throw new Exception("敏感词库文件不存在");
            }
        }catch (Exception e){
            throw e;
        }finally {
            read.close();
        }
        return set;
    }
}
