package com.example.controller;

import com.example.entity.User;
import com.example.service.AdminService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

@WebServlet("/managex/updatedeadline")
public class UpdateDeadlineServlet extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(UpdateDeadlineServlet.class.getName());
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String time = req.getParameter("time");
        LOGGER.info(time);
        String number = ((User)req.getSession().getAttribute("user")).getNumber();
        AdminService.updateDeadline(time, number);
        req.getServletContext().setAttribute("deadline", time);
        req.getServletContext().setAttribute("deadlineT", LocalDateTime.parse(time, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }
}
