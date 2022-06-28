package com.merit.meritShop.common.domain;

public enum ResultCode {

    Success(0, "성공"),


    //카테고리 관련 오류 100번대
    CATEGORY_ALREADY_EXISTS(103, "이미 존재하는 카테고리입니다."),
    //유저 관련 오류 200번대
    USER_NOT_EXISTS(200, "존재하지 않는 유저입니다"),
    NOT_LOGIN(201,"다시 로그인해 주세요"),
    //주문 관련 오류
    ORDER_NOT_EXISTS(300, "존재하지 않는 주문입니다."),
    //
    SCRAP_ALREADY_EXISTS(400,"이미 찜한 상품입니다."),
    SCRAP_ALREADY_DELETE(401,"이미 삭제된 찜입니다."),

    //리뷰 관련 오류
    REVIEW_NOT_EXISTS(500,"이미 삭제된 리뷰입니다."),
    //기타 에러
    DB_ERROR(9998, "DB 오류입니다."),
    ETC_ERROR(9999, "시스템 오류입니다.");

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public <T> Result<T> result(T resultObject) {
        return new Result<T>(resultObject, this);
    }

    public <T> Result<T> result() {
        return new Result<T>(null, this);
    }

    @Override
    public String toString() {
        return "{code=" + code + ", message=" + message + "}";
    }
}
