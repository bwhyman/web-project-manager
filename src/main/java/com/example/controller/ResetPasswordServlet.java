package com.example.controller;

import com.example.service.AdminService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managex/resetpassword")
public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("number");
        int r = AdminService.resetPassword(number);
        if (r == 0) {
            resp.getWriter().print("学号不存在！");
            return;
        }
        resp.getWriter().print("更新成功！");
    }
}
