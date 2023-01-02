package com.example.controller;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.service.ProjectService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/managerx/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        Project project = ProjectService.getProject(user.getId());
        req.setAttribute("project", project);
        if (user.getRole() == 1) {
            req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
        resp.getWriter().print("ok");
    }
}
