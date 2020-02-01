package com.wivs.controllter;

import com.wivs.service.CityDataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getCity")
public class City extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String line = null;
        String city = "";
        while ((line = reader.readLine()) != null) {
            city = line.split("\"")[3];
            CityDataService cityDataService = new CityDataService();
            String data = cityDataService.selectCityData(city);
            resp.setContentType("text/html;charset=utf-8");
            PrintWriter writer = resp.getWriter();
            writer.write(data);
        }
    }
}
