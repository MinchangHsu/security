package com.caster.security.enums;

import lombok.Getter;

@Getter
public enum SuccessCodeMsg {
    COMMON_FAIL(0, "回應失敗"),
    COMMON_OK(1, "回應成功"),
    PAYMENT_REQ_SUCCESS(1, "支付請求成功"),
    PAYMENT_REDIRECT_BANK_PAGE(1, "轉導至網銀選單頁面"),
    PAYMENT_PAIED_SUCCESS(1, "訂單已支付"),
    WITHDRAW_APPLY_SUCCESS(1, "代付申請成功"),
    WITHDRAW_SUCCESS(1, "代付出款成功"),
    WITHDRAW_NOTIFY_SUCCESS(1, "代付通知成功"),
    PAYMENT_NOTIFY_SUCCESS(1, "支付通知成功");

    private int code;
    private String msg;

    SuccessCodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

}
