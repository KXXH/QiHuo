package utils;

import java.util.HashMap;

/**
 * Created by zjm97 on 2019/5/31.
 */
public class locaionManager {

    public static String proviceNameToPinYin(String p){
        System.out.println("Location="+p);
        if(p==null||p.length()==0) return "unknown";
        HashMap<String,String> m=new HashMap<>();
        m.put("未知","unknown");
        m.put("四川","si_chuan");
        m.put("北京","bei_jing");
        m.put("新疆","xin_jiang");
        m.put("西藏","xi_zang");
        m.put("内蒙古","nei_meng_gu");
        m.put("青海","qing_hai");
        m.put("黑龙江","hei_long_jiang");
        m.put("甘肃","gan_su");
        m.put("云南","yun_nan");
        m.put("广西","guang_xi");
        m.put("湖南","hu_nan");
        m.put("陕西","shan_xi_1");
        m.put("广东","guang_dong");
        m.put("吉林","ji_lin");
        m.put("河北","he_bei");
        m.put("湖北","hu_bei");
        m.put("贵州","gui_zhou");
        m.put("山东","shan_dong");
        m.put("江西","jiang_xi");
        m.put("河南","he_nan");
        m.put("辽宁","liao_ning");
        m.put("山西","shan_xi_2");
        m.put("安徽","an_hui");
        m.put("福建","fu_jian");
        m.put("浙江","zhe_jiang");
        m.put("江苏","jiang_su");
        m.put("重庆","chong_qing");
        m.put("宁夏","ning_xia");
        m.put("海南","hai_nan");
        m.put("台湾","tai_wan");
        m.put("天津","tian_jin");
        m.put("上海","shang_hai");
        m.put("香港","xiang_gang");
        m.put("澳门","ao_men");
        return m.get(p);

    }
}
