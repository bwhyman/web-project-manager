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

@WebServlet("/managerx/submitself")
public class SubmitSelfServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String self = req.getParameter("selfaddress");
        User user = (User) req.getSession().getAttribute("user");
        Project project = ProjectService.getProject(user.getId());
        int pid = project == null ? ProjectService.addProject(user.getId()) : project.getId();
        ProjectService.updateProjectSelf(pid, self);
        ProjectsCache.setProjects(null);
        resp.getWriter().print("ok");
    }
}
