package com.wivs.service;

import com.wivs.dao.Message;

public class MessageService {
    private static Message message;
    static {
        message = new Message();
    }

    public void addFriend (String fid, String tid) {
        message.insertMessage(fid, tid, "", true);
    }

    public String getMessage (String acc) {
        String msg = message.getMessage(acc);
        return msg;
    }

    public void setRead (String fid, String tid, boolean ifnotice) {
        message.setRead(fid, tid, ifnotice);
    }

    public String getChatMessage (String acc, String friend) {
        return message.getChatMessage(acc, friend);
    }

    public void sendMessage (String fid, String tid, String msg) {
        message.insertMessage(fid, tid, msg, false);
    }
}
