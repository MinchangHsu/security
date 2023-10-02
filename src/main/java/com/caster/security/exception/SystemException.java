package com.caster.security.exception;


import com.caster.security.enums.ErrorCodeMsg;

/**
 * Author: Caster
 * Date: 2022/7/8
 * Comment:
 */
public class SystemException extends RuntimeException {

    private static final long serialVersionUID = 2066135405823601035L;
    private String errorMsg;
    private Integer errorCode;

    private SystemException() {
        super();
    }

    public SystemException(ErrorCodeMsg enumCodeMsg, String... params) {
        super(enumCodeMsg.getErrorMsg(params));
        this.errorMsg = enumCodeMsg.getErrorMsg(params);
        this.errorCode = enumCodeMsg.getErrorCode();
    }

    public SystemException(String errorMsg, int errorCode) {
        super(errorMsg);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public SystemException(String enumCodeMsg) {
        super(enumCodeMsg);
        this.errorMsg = enumCodeMsg;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

}
