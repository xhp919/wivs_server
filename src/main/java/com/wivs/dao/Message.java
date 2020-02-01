package com.wivs.dao;

import com.wivs.tools.UserDBPoolTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Message {
    public void insertMessage (String fid, String tid, String msg, Boolean ifnotice) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into message (fid, tid, msg, ifnotice) values (?, ?, ?, ?)";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, fid);
            statement.setString(2, tid);
            statement.setString(3, msg);
            statement.setBoolean(4, ifnotice);
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

    public String getMessage (String acc) {
        String res = "";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from message where tid = ? and ifread = false  order by t";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, acc);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res += "{\"name\":\"" + resultSet.getString("fid") + "\",\"msg\":\"" + resultSet.getString("msg") + "\",\"ifnotice\":\"" + resultSet.getBoolean("ifnotice") + "\",\"time\":\"" + resultSet.getTimestamp("t") + "\"}";
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

    public void setRead (String fid, String tid, boolean ifnotice) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "update message set ifread = true where tid = ? and fid = ? and ifnotice = ?";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, tid);
            statement.setString(2, fid);
            statement.setBoolean(3, ifnotice);
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

    public String getChatMessage (String acc, String friend) {
        String msg = "";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from message where tid = ? and fid = ? and ifnotice = false or tid = ? and fid = ? and ifnotice = false order by t";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, acc);
            statement.setString(2, friend);
            statement.setString(3, friend);
            statement.setString(4, acc);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                msg += "{\"name\":\"" + resultSet.getString("fid") + "\",\"msg\":\"" + resultSet.getString("msg") + "\",\"time\":\"" + resultSet.getTimestamp("t") + "\"}";
                msg += ",";
            }
            if (!msg.equals("")) {
                msg = "[" + msg.substring(0, msg.length() - 1) + "]";
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
        return msg;
    }
}
