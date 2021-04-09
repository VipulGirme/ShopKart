package com.example.polls.common;

public class CommonResponseHeader {
	private Boolean successStatus;
    private String message;

    public CommonResponseHeader(Boolean success, String message) {
        this.successStatus = success;
        this.message = message;
    }

    public Boolean getSuccess() {
        return successStatus;
    }

    public void setSuccess(Boolean success) {
        this.successStatus = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
