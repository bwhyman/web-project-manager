package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@WebServlet("/manager/photosettings")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 5)
public class PhotoSettingsServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("file");
        User user = (User) req.getSession().getAttribute("user");
        part.getInputStream().readAllBytes();
        String s = Base64.getEncoder().encodeToString(part.getInputStream().readAllBytes());
        UserService.updatePhoto(user.getId(), s);
        user.setPhoto(s);
        req.setAttribute("photo", s);
        req.getRequestDispatcher("/WEB-INF/photosettings.jsp").forward(req, resp);
    }
}
