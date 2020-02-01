package com.wivs.service;

import com.wivs.dao.Relation;

public class RelationService {
    private static Relation relation;
    static {
        relation = new Relation();
    }

    public String getFriends (String acc) {
        String res = "";
        res = relation.getFriends(acc);
        return res;
    }

    public void deleteFriends (String acc, String friend) {
        relation.deleteFriend(acc, friend);
        relation.deleteFriend(friend, acc);
    }

    public void addFriend (String acc, String friend) {
        relation.addFriend(acc, friend);
        relation.addFriend(friend, acc);
    }
}
