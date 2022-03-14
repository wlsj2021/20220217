package com.wlsj2021.myapplication.msg;

public class EventBusMsg {

    private String msgCode;
    private String msgName;

    public EventBusMsg(String msgCode, String msgName) {
        this.msgCode = msgCode;
        this.msgName = msgName;
    }

    public String getMsgCode() {
        return msgCode;
    }

    public void setMsgCode(String msgCode) {
        this.msgCode = msgCode;
    }

    public String getMsgName() {
        return msgName;
    }

    public void setMsgName(String msgName) {
        this.msgName = msgName;
    }

    @Override
    public String toString() {
        return "EventBusMsg{" +
                "msgCode='" + msgCode + '\'' +
                ", msgName='" + msgName + '\'' +
                '}';
    }
}
