package com.wivs.controllter;

import com.wivs.service.AccountService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/account/modify")
public class Modify extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        BufferedReader reader = req.getReader();
        String line = null;
        while ((line = reader.readLine()) != null) {
            String acc = line.split("\"")[3];
            String oldPsw = line.split("\"")[7];
            String newPsw = line.split("\"")[11];
            AccountService accountService = new AccountService();
            boolean res = accountService.modifyPsw(acc, oldPsw, newPsw);
            PrintWriter writer = resp.getWriter();
            if (res) {
                writer.write("true");
            } else {
                writer.write("false");
            }
        }
    }
}
