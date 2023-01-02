package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managerx/changepwd")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pwd = req.getParameter("pwd");
        User user = (User) req.getSession().getAttribute("user");
        UserService.updatePassword(user.getId(), pwd);
    }
}
