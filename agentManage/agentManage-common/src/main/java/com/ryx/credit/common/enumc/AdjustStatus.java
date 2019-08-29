package com.ryx.credit.common.enumc;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 业务平台类型
 * Created by liudh on 2018/9/58.
 */
public enum AdjustStatus {

    WTZ(new BigDecimal("1"),"未调整"),
    YTZ(new BigDecimal("2"),"已调整"),
    TZZ(new BigDecimal("3"),"调整中"),
    TZSB(new BigDecimal("4"),"调整失败"),
    JLBCZ(new BigDecimal("5"),"记录不存在"),
    WLDTZ(new BigDecimal("6"),"未联动调整"),
    WCDJG(new BigDecimal("7"),"未查到结果"),
    JJTZ(new BigDecimal("8"),"调整拒绝");

    public BigDecimal key;
    public String msg;

    AdjustStatus(BigDecimal k, String s){
        key = k;
        msg = s;
    }

    /**
     * 取得枚举对象值
     * @return 枚举对象值
     */
    public BigDecimal getValue() {
        return this.key;
    }
    /**
     * 取得缓存内容
     * @return 缓存内容
     */
    public String getContent() {
        return this.msg;
    }


    public static String getContentByValue(BigDecimal value){
        AdjustStatus[] busType = AdjustStatus.values();
        for(AdjustStatus bt : busType){
            if(bt.key.compareTo(value)==0){
                return bt.msg;
            }
        }
        return "";
    }

    public static BigDecimal getValueByContent(String value){
        AdjustStatus[] busType = AdjustStatus.values();
        for(AdjustStatus bt : busType){
            if(bt.msg.equals(value)){
                return bt.key;
            }
        }
        return null;
    }

    public static Map<BigDecimal, String>  getValueMap(){
        Map<BigDecimal, String> hashMap = new LinkedHashMap<>();
        AdjustStatus[] busType = AdjustStatus.values();
        for(AdjustStatus bt : busType){
            hashMap.put(bt.key,bt.msg);
        }
        return hashMap;
    }

}
