package com.wivs.service;

import com.wivs.tools.HttpClientPool;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class WindDataService {
    public String getWindData () {
        CloseableHttpClient httpClient = HttpClientPool.getHttpClient();
        HttpGet httpGet = new HttpGet("http://www.tf110.com/data/gfs/gfs.json");
        CloseableHttpResponse response = null;
        String data = null;
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                data = EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
