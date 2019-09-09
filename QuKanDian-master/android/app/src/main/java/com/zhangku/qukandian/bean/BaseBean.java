package com.zhangku.qukandian.bean;

/**
 * 创建者          xuzhida
 * 创建日期        2018/7/13
 * 你不注释一下？
 */
public class BaseBean<T> {
    private boolean success;
    private String message;
    private ErrorBean error;
    private T result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ErrorBean getError() {
        return error;
    }

    public static class ErrorBean{
        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return "ErrorBean{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "BaseBean{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
