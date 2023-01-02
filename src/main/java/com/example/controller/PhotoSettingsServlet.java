package com.example.controller;

import com.example.ProjectsCache;
import com.example.entity.User;
import com.example.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/managerx/photosettings")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5)
public class PhotoSettingsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String s = req.getParameter("base64");
        User user = (User) req.getSession().getAttribute("user");
        UserService.updatePhoto(user.getId(), s);
        ProjectsCache.setProjects(null);
        resp.getWriter().print("ok");
    }
}
