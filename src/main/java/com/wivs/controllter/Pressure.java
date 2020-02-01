package com.wivs.controllter;

import com.wivs.service.PressureDataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getPressure")
public class Pressure extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PressureDataService pressureDataService = new PressureDataService();
        String data = pressureDataService.getData();
        PrintWriter writer = resp.getWriter();
        writer.write(data);
    }
}
