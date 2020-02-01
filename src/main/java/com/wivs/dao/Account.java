package com.wivs.dao;

import com.wivs.tools.UserDBPoolTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Account {
    public boolean insertUser (String acc, String psw) {
        boolean ifSuccess = true;
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into account (uid, psw) values (?, ?)";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, acc);
            statement.setString(2, psw);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            ifSuccess = false;
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return ifSuccess;
    }

    public String getAllUser () {
        String res = "";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select uid from account";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res += "\"" + resultSet.getString("uid") + "\"";
                res += ",";
            }
            if (!res.equals("")) {
                res = "[" + res.substring(0, res.length() - 1) + "]";
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return res;
    }

    public String selectPsw (String acc) {
        String psw = "";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select psw from account where uid = ? and status = false";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, acc);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                psw = resultSet.getString("psw");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return psw;
    }

    public void setStatus (String acc, boolean status) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update account set status = ? where uid = ?";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setBoolean(1, status);
            statement.setString(2, acc);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void modifyPsw (String acc, String psw) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update account set psw = ? where uid = ?";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, psw);
            statement.setString(2, acc);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStatusToDefault () {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update account set status = false";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                statement.close();
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
