package com.example.controller;

import com.example.ProjectsCache;
import com.example.entity.Project;
import com.example.entity.User;
import com.example.service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebServlet("/managerx/profile")
public class ProfileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user.getRole() == 1) {
            req.getRequestDispatcher("/WEB-INF/admin.jsp").forward(req, resp);
            return;
        }
        Project project = ProjectService.getProject(user.getId());
        req.setAttribute("project", project);
        req.getRequestDispatcher("/WEB-INF/profile.jsp").forward(req, resp);
    }
}
