package com.practice.entity;

/**
 * Created by gaofeng on 2016-12-08.
 */

public class Message {
    String message;
    boolean receive;

    public Message() {
        message="";
        receive=false;
    }

    public Message(String message, boolean receive) {
        this.message = message;
        this.receive = receive;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isReceive() {
        return receive;
    }

    public void setReceive(boolean receive) {
        this.receive = receive;
    }
}
