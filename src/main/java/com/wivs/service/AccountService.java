package com.wivs.service;

import com.wivs.dao.Account;

public class AccountService {
    private static Account account;

    static {
        account = new Account();
    }

    public boolean validePsw (String acc, String psw) {
        boolean res = false;
        String relPsw = account.selectPsw(acc);
        if (relPsw.equals(psw)) {
            res = true;
        }
        return res;
    }

    public void setOnline (String acc) {
        account.setStatus(acc, true);
    }

    public void setOutline (String acc) {
        account.setStatus(acc, false);
    }

    public boolean modifyPsw (String acc, String oldPsw, String newPsw) {
        boolean ifSuccess = false;
        boolean res = validePsw(acc, oldPsw);
        if (res) {
            account.modifyPsw(acc, newPsw);
            ifSuccess = true;
        }
        return ifSuccess;
    }

    public void setStatusToDefault () {
        account.setStatusToDefault();
    }

    public boolean register (String acc, String psw) {
        return account.insertUser(acc, psw);
    }
}
