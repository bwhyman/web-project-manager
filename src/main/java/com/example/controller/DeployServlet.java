package com.example.controller;

import com.example.ProjectsCache;
import com.example.service.AdminService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managerx/deploy")
public class DeployServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String number = req.getParameter("snumber");
        AdminService.removeDeploy(number);
        ProjectsCache.setProjects(null);
        resp.getWriter().print("ok");
    }
}
