package com.example.controller;

import com.example.ProjectsCache;
import com.example.service.ProjectService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/projects")
public class ProjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (ProjectsCache.listProjects() == null) {
            ProjectsCache.setProjects(ProjectService.listProjects());
        }
        req.setAttribute("projects",ProjectsCache.listProjects());
        req.getRequestDispatcher("/WEB-INF/project-table.jsp").forward(req, resp);
    }
}
