package com.wivs.tools;

import com.wivs.spring.SpringContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HttpClientPool {
    private static PoolingHttpClientConnectionManager cm;

    static {
        ClassPathXmlApplicationContext context = SpringContext.context;
        cm = (PoolingHttpClientConnectionManager) context.getBean("poolingHttpClientConnectionManager");
    }

    public static CloseableHttpClient getHttpClient () {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm).build();
        return httpClient;
    }
}
