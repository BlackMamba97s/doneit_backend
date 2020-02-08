package com.sini.doneit.model.dto;

public class SocketChatMessage {

    private String userTo;
    private String userFrom;
    private String content;

    public SocketChatMessage() {
    }

    public String getUserTo() {
        return userTo;
    }

    public void setUserTo(String userTo) {
        this.userTo = userTo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(String userFrom) {
        this.userFrom = userFrom;
    }

    @Override
    public String toString() {
        return "SocketChatMessage{" +
                "userTo='" + userTo + '\'' +
                ", userFrom='" + userFrom + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
