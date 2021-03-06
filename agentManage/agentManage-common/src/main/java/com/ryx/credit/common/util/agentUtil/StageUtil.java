package com.ryx.credit.common.util.agentUtil;

import com.ryx.credit.common.util.FastMap;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by RYX on 2018/7/20.
 */
public class StageUtil {

    public static List<Map> stageOrder(BigDecimal amount, int count, Date date, int day){
        if(count==0)count=1;
        BigDecimal item =  amount.divide(new BigDecimal(count),8,BigDecimal.ROUND_HALF_UP);
        item = item.setScale(2,BigDecimal.ROUND_HALF_UP);
        BigDecimal temp = new BigDecimal(0);
        for (int i=1;i<= count;i++){
            temp = temp.add(item);
        }
        if(temp.compareTo(amount) != 0){
            temp = amount.subtract(temp);
        }else{
            temp = new BigDecimal(0);
        }
        List<Map> data = new ArrayList<>();
        Calendar c = Calendar.getInstance();
        Date subMonth = subMonth(date);
        c.setTime(subMonth);
        c.set(Calendar.DAY_OF_MONTH,day);
        for (int i=1;i<= count;i++){
            //第一个月
            FastMap map = FastMap.fastSuccessMap();
            map.putKeyV("date",c.getTime());
            map.putKeyV("count",new BigDecimal(i));
            map.putKeyV("item",i==1?item.add(temp):item);
            data.add(map);
            //累加1个月
            c.add(Calendar.MONTH,1);
        }
        return data;
    }

    public static Date subMonth(Date date) {
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(date);
        rightNow.add(Calendar.MONTH, 1);
        Date planDate = rightNow.getTime();
        return planDate;
    }

    public static List<Map> stageOrderCustom(BigDecimal amount, int count, Date date, int day,List<BigDecimal> stages){
        if(count==0)count=1;
        BigDecimal temp = new BigDecimal(0);
        List<Map> data = new ArrayList<>();
        //计算每一笔的分期金额
        for (BigDecimal stage:stages){
            //分期的比例
            BigDecimal Proportion = stage.divide(amount,8,BigDecimal.ROUND_HALF_UP);
            BigDecimal item =  amount.divide(new BigDecimal(count),8,BigDecimal.ROUND_HALF_UP).multiply(Proportion);
            item = item.setScale(2,BigDecimal.ROUND_HALF_UP);

            for (int i=1;i<= count;i++){
                temp = temp.add(item);
            }
            if(temp.compareTo(amount) != 0){
                temp = amount.subtract(temp);
            }else{
                temp = new BigDecimal(0);
            }

            Calendar c = Calendar.getInstance();
            Date subMonth = subMonth(date);
            c.setTime(subMonth);
            c.set(Calendar.DAY_OF_MONTH,day);
            for (int i=1;i<= count;i++){
                //第一个月
                FastMap map = FastMap.fastSuccessMap();
                map.putKeyV("date",c.getTime());
                map.putKeyV("count",new BigDecimal(i));
                map.putKeyV("item",i==1?item.add(temp):item);
                data.add(map);
                //累加1个月
                c.add(Calendar.MONTH,1);
            }
        }

        return data;
    }


    public static void main(String[] args){
//        stageOrder(new BigDecimal("100"),3,new Date(),15);
        List<BigDecimal> stages = new ArrayList<>();
        stages.add(new BigDecimal("20"));
        stages.add(new BigDecimal("35"));
        stages.add(new BigDecimal("45"));
    stageOrderCustom(new BigDecimal("100"),3,new Date(),15,stages);
    }


}
