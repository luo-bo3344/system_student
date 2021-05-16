package com.lb.utils;

public enum DeleteCode {
    DELETE_SUCCESS("删除成功"),
    DELETE_FAIL("删除失败");

    private final String message;


    DeleteCode(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
