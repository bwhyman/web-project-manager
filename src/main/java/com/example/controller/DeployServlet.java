package com.example.controller;

import com.example.ProjectsCache;
import com.example.service.AdminService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
