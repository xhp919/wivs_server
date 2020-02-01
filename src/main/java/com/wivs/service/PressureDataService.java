package com.wivs.service;

import com.wivs.dao.PressureData;
import com.wivs.tools.HttpClientPool;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Calendar;

public class PressureDataService {
    private static PressureData pressureData;
    static {
        pressureData = new PressureData();
    }

    public String selectPressureData() {
        String data = pressureData.selectData();
        return data;
    }
    public String getData () {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DATE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        boolean ifSuccess = false;
        CloseableHttpClient httpClient = HttpClientPool.getHttpClient();
        String data = null;
        int count = 24;
        while (!ifSuccess) {
            count--;
            String url;
            String m = "0" + month;
            m = m.substring(m.length() - 2);
            String d = "0" + day;
            d = d.substring(d.length() - 2);
            String h = "0" + hour;
            h = h.substring(h.length() - 2);
            url = "https://ims.windy.com/ecmwf-hres/" + year + "/" + m + "/" + d + "/" + h + "/siw0/0/0/pressure-surface.json";
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = null;
            try {
                response = httpClient.execute(httpGet);
                if (response.getStatusLine().getStatusCode() == 200) {
                    HttpEntity entity = response.getEntity();
                    data = EntityUtils.toString(entity);
                    ifSuccess = true;
                } else if (!(count == 0)) {
                    hour--;
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
        }
        return data;
    }
}
