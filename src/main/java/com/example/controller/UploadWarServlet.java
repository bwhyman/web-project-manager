package com.example.controller;

import com.example.entity.Project;
import com.example.entity.User;
import com.example.service.ProjectService;

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

@WebServlet("/managerx/uploadwar")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10)
public class UploadWarServlet extends HttpServlet {
    // Path base = Path.of("D:/apache-tomcat-9.0.38/webapps/");
    // Path base = Path.of("E:/");
    Path base = Path.of("/usr/local/tomcat/webapps/");
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part p = req.getPart("file");
        String index = req.getParameter("index");
        User user = (User) req.getSession().getAttribute("user");
        Project project = ProjectService.getProject(user.getId());
        int pid = project == null ? ProjectService.addProject(user.getId()) : project.getId();

        ProjectService.updateProject(pid, index);
        Path path = base.resolve(Path.of(user.getNumber() + ".war"));
        Files.write(path, p.getInputStream().readAllBytes());
    }
}
