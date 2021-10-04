package com.example.controller;

import com.example.ProjectsCache;
import com.example.entity.User;
import com.example.service.ProjectService;
import com.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/managerx/photosettings")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5)
public class PhotoSettingsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = req.getParameter("base64");
        User user = (User) req.getSession().getAttribute("user");
        UserService.updatePhoto(user.getId(), s);
        ProjectsCache.setProjects(ProjectService.listProjects());
        resp.getWriter().print("ok");
    }
}
