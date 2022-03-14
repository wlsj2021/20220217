package com.wlsj2022.recyclerviewim;

import org.w3c.dom.Text;

public class Message {
    public static final int TYPE_RIGHT = 1;
    public static final int TYPE_LEFT = 2;


    private String text;
    private int msgType;

    public Message(String text, int msgType) {
        this.text = text;
        this.msgType = msgType;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "text='" + text + '\'' +
                ", msgType=" + msgType +
                '}';
    }
}

