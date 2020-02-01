package com.wivs.dao;

import com.wivs.tools.UserDBPoolTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Relation {
    public String getFriends (String acc) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select fid from relation where uid = ?";
        String res = "";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, acc);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                res += "\"" + resultSet.getString("fid") + "\"";
                res += ",";
            }
            if (!res.equals("")) {
                res = res.substring(0, res.length() - 1);
            }
            res = "[" + res + "]";
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

    public void deleteFriend (String acc, String friend) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "delete from relation where uid = ? and fid = ?";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, acc);
            statement.setString(2, friend);
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

    public void addFriend (String acc, String friend) {
        Connection connection = null;
        PreparedStatement statement = null;
        String sql = "insert into relation (uid, fid) values (?, ?)";
        try {
            connection = UserDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setString(1, acc);
            statement.setString(2, friend);
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
