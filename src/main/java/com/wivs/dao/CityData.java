package com.wivs.dao;

import com.wivs.tools.DataDBPoolTool;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CityData {
    public String selectDataByName (String cityName) {
        String data = "";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String sql = "select * from city where n like '%" + cityName + "%'";
        try {
            connection = DataDBPoolTool.getConnection();
            statement = connection.prepareStatement(sql);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                data += "{\"n\":\"" + resultSet.getString("n") + "\",\"coordinate\":[" + resultSet.getString("x") + "," + resultSet.getString("y") + "]}";
                data += ",";
            }
            if (data != "") {
                data = data.substring(0, data.length() - 1);
                data = "[" + data + "]";
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
        return data;
    }
}
