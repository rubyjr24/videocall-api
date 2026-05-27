package com.rubyjr.videocall.dto.requests;

public class SignalMessage {

    private String type;
    private String from;
    private String to;
    private Long roomId;
    private Object payload;

    public SignalMessage() {
    }

    public SignalMessage(String type, String from, String to, Long roomId, Object payload) {
        this.type = type;
        this.from = from;
        this.to = to;
        this.roomId = roomId;
        this.payload = payload;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
