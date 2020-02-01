package com.wivs.controllter;

import com.wivs.dao.Account;
import com.wivs.dao.Message;
import com.wivs.dao.Relation;
import com.wivs.service.AccountService;
import com.wivs.service.MessageService;
import com.wivs.service.RelationService;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint("/user")
public class Socket {
    private static int count = 0;
    private static Map<Session, String> map;
    private static AccountService accountService;
    private static RelationService relationService;
    private static MessageService messageService;

    static {
        map = new HashMap<Session, String>();
        accountService = new AccountService();
        relationService = new RelationService();
        messageService = new MessageService();

        accountService.setStatusToDefault();
    }

    @OnOpen
    public void onOpen (Session session) {
        count++;
    }

    @OnMessage
    public void onMessage (Session session, String message) {
        int index = message.indexOf(":");
        String type = message.substring(0, index);
        String [] param = message.substring(index + 1, message.length()).split(",");
        if (type.equals("login")) {
            doLogin(session, param);
        }
        if (type.equals("getFriends")) {
            updateFriendsList(param[0]);
        }
        if (type.equals("getMessage")) {
            sendMessage(param[0]);
        }
        if (type.equals("deleteFriend")) {
            doDeleteFriend(param[0], param[1]);
        }
        if (type.equals("getUsers")) {
            String users = getUsers(param[0], param[1]);
            try {
                session.getBasicRemote().sendText("user:" + users);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (type.equals("addFriend")) {
            doAddFriend(param[0], param[1]);
        }
        if (type.equals("agree")) {
            doAgree(param[0], param[1]);
        }
        if (type.equals("refuse")) {
            doRefuse(param[0], param[1]);
        }
        if (type.equals("view")) {
            doView(param[0], param[1]);
        }
        if (type.equals("chat")) {
            doChat(param[0], param[1]);
        }
        if (type.equals("send")) {
            doSend(param[0], param[1], param[2]);
        }
    }

    @OnClose
    public void onClose (Session session) {
        String time = getDate();
        count--;
        if (map.get(session) != null) {
            System.out.println(time + " " + map.get(session) + "断开连接，当前" + count + "个连接");
            accountService.setOutline(map.get(session));
            map.remove(session);
        }
    }

    @OnError
    public void onError(Throwable e, Session session){}

    public String getDate () {
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String time = formatter.format(date);
        return time;
    }

    public void updateFriendsList (String acc) {
        String friends = relationService.getFriends(acc);
        try {
            for (Session session:map.keySet()) {
                String value = map.get(session);
                if (value.equals(acc)) {
                    session.getBasicRemote().sendText("friends:" + friends);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doLogin (Session session, String [] param) {
        boolean res = accountService.validePsw(param[0], param[1]);
        if (res) {
            accountService.setOnline(param[0]);
            map.put(session, param[0]);
            try {
                session.getBasicRemote().sendText("login:true");
            } catch (IOException e) {
                e.printStackTrace();
            }
            String time = getDate();
            System.out.println(time + " " + param[0] + "连接，当前" + count + "个连接");
        } else {
            try {
                session.getBasicRemote().sendText("login:false");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void doDeleteFriend (String acc, String friend) {
        relationService.deleteFriends(acc, friend);
        updateFriendsList(acc);
        updateFriendsList(friend);
    }

    public String getUsers (String acc, String inputText) {
        String res = "";
        Account account = new Account();
        String allUser = account.getAllUser();
        Relation relation = new Relation();
        String friends = relation.getFriends(acc);
        String [] usersArr = allUser.substring(1, allUser.length() - 1).split(",");
        String [] friendsArr = friends.substring(1, friends.length() - 1).split(",");
        for (String user :usersArr) {
            boolean ifIn = false;
            for (String friend :friendsArr) {
                if (friend.equals(user)) {
                    ifIn = true;
                }
            }
            if (!ifIn && user.indexOf(inputText) != -1 && !user.equals("\"" + acc + "\"")) {
                res += user;
                res += ",";
            }
        }
        if (!res.equals("")) {
            res = res.substring(0, res.length() - 1);
        }
        res = "[" + res + "]";
        return res;
    }

    public void sendMessage (String tid) {
        String message = messageService.getMessage(tid);
        try {
            for (Session session:map.keySet()) {
                String value = map.get(session);
                if (value.equals(tid)) {
                    session.getBasicRemote().sendText("message:" + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doAddFriend (String fid, String tid) {
        messageService.addFriend(fid, tid);
        sendMessage(tid);
    }

    public void doAgree (String uid, String fid) {
        relationService.addFriend(uid, fid);
        messageService.setRead(fid, uid, true);
        sendMessage(uid);
        updateFriendsList(uid);
        updateFriendsList(fid);
    }

    public void doRefuse (String uid, String fid) {
        messageService.setRead(fid, uid, true);
        sendMessage(uid);
    }

    public void doView (String uid, String fid) {
        messageService.setRead(fid, uid, false);
        sendMessage(uid);
        sendMessage(fid);
    }

    public void doChat (String acc, String friend) {
        String msg = messageService.getChatMessage(acc, friend);
        try {
            for (Session session:map.keySet()) {
                String value = map.get(session);
                if (value.equals(acc)) {
                    session.getBasicRemote().sendText("chat:" + msg);
                }
            }
            doView(acc, friend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void doSend (String fid, String tid, String msg) {
        messageService.sendMessage(fid, tid, msg);
        sendMessage(tid);
        doChat(fid, tid);
        doChat(tid, fid);
    }
}
