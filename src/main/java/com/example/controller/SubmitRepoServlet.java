package com.example.controller;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.service.ProjectService;
import com.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/manager/submitrepo")
public class SubmitRepoServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        String repo = req.getParameter("repositoryurl");
        UserService.updateRepository(user.getId(), repo);
        user.setRepositoryUrl(repo);
    }
}
