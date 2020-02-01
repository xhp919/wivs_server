package com.wivs.controllter;

import com.wivs.service.WindDataService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/getWindData")
public class Wind extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        WindDataService windDataService = new WindDataService();
        String windData = windDataService.getWindData();
        PrintWriter writer = resp.getWriter();
        writer.write(windData);
    }
}
