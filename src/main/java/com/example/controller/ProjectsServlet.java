package com.example.controller;

import com.example.ProjectsCache;
import com.example.entity.Project;
import com.example.service.ProjectService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/projects")
public class ProjectsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("projects",
                Optional.ofNullable(ProjectsCache.listProjects())
                        .orElse(ProjectService.listProjects()));
        req.getRequestDispatcher("/WEB-INF/project-table.jsp").forward(req, resp);
    }
}
