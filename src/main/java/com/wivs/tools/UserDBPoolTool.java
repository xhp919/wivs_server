package com.wivs.tools;

import com.alibaba.druid.pool.DruidDataSource;
import com.wivs.spring.SpringContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDBPoolTool {
    private static DruidDataSource ds;

    static {
        ClassPathXmlApplicationContext context = SpringContext.context;
        ds = (DruidDataSource) context.getBean("druid_user");
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
