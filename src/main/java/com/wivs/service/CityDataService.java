package com.wivs.service;

import com.wivs.dao.CityData;

public class CityDataService {
    private static CityData cityData;
    static {
        cityData = new CityData();
    }

    public String selectCityData (String cityName) {
        String data = cityData.selectDataByName(cityName);
        return data;
    }
}
