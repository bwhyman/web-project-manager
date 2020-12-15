package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("number");
        String pwd = req.getParameter("pwd");
        User user = UserService.getUser(number, pwd);
        if (user == null) {
            resp.setStatus(501);
            return;
        }
        req.getSession().setAttribute("user", user);

        if (user.getRole() == 1) {
            req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }
}
