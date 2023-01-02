package com.example.controller;

import com.example.ProjectsCache;
import com.example.entity.User;
import com.example.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managerx/submitrepo")
public class SubmitRepoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String repo = req.getParameter("repositoryurl");
        UserService.updateRepository(user.getId(), repo);
        ProjectsCache.setProjects(null);
        user.setRepositoryUrl(repo);
        resp.getWriter().print("ok");
    }
}
