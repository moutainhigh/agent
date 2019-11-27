package com.ryx.credit.common.enumc;

import java.math.BigDecimal;

/**
 * Created by RYX on 2018/7/21.
 * 付款明细类型 PAYMENTTYPE
 */
public enum PaymentType {

    SF("SF","首付"),
    FRFQ("FRFQ","分润分期"),
    DKFQ("DKFQ","打款分期"),
    TK("TK","退款"),
    GZ("GZ","挂账"),
    DK("DK","打款");

    public String code;

    public String msg;

    PaymentType(String c, String m){
        this.code=c;
        this.msg =m;
    }
    public static String getPaymentTypeValue(String value){
        PaymentType[] paymentType = PaymentType.values();
        for(PaymentType cc : paymentType){
            if(cc.code.equals(value)){
                return cc.msg;
            }
        }
        return "";
    }
}
