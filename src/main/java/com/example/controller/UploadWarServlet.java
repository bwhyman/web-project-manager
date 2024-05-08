package com.example.controller;

import com.example.ProjectsCache;
import com.example.entity.Project;
import com.example.entity.User;
import com.example.service.ProjectService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@WebServlet("/managerx/uploadwar")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10)
public class UploadWarServlet extends HttpServlet {
    Path base = Path.of("/usr/local/tomcat/webapps/");

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part p = req.getPart("file");
        User user = (User) req.getSession().getAttribute("user");
        Project project = ProjectService.getProject(user.getId());
        if (project == null) {
            ProjectService.addProject(user.getId());
        } else {
            ProjectService.updateProjectUpdateTime(project.getId());
        }
        ProjectsCache.setProjects(null);
        Path path = base.resolve(Path.of(user.getNumber() + ".war"));
        try {
            Files.write(path, p.getInputStream().readAllBytes());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        resp.getWriter().print("ok");
    }
}
