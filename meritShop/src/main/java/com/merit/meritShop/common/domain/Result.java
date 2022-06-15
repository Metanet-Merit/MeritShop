package com.merit.meritShop.common.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class Result<T> {

    private final T resultObject;
    private final ResultCode resultCode;

    public Result(T resultObject, ResultCode errorCode) {
        super();
        this.resultObject = resultObject;
        this.resultCode = errorCode;
    }

    @JsonIgnore
    public T getResultObject() {
        return resultObject;
    }

    @JsonIgnore
    public ResultCode getResultCode() {
        return resultCode;
    }

    public int getRtnCd() {
        return resultCode.getCode();
    }

    public String getRtnMsg() {
        return resultCode.getMessage();
    }

    public T getRtnObj() {
        return resultObject;
    }

    @JsonIgnore
    public boolean isSuccess() {
        return ResultCode.Success.equals(this.resultCode);
    }

    @JsonIgnore
    public boolean isNotSuccess() {
        return !isSuccess();
    }

    /**
     * @return
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "{resultObject=" + resultObject + ", resultCode=" + resultCode + "}";
    }

}
