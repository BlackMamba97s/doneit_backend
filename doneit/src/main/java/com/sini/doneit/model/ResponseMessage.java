package com.sini.doneit.model;

import org.apache.coyote.Response;

public class ResponseMessage {

    private String message;
    private Integer messageCode;


    public ResponseMessage() {
    }

    public ResponseMessage(String message) {
        this.message = message;
    }

    public ResponseMessage(String message, Integer messageCode) {
        this.message = message;
        this.messageCode = messageCode;
    }

    public Integer getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(Integer messageCode) {
        this.messageCode = messageCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
