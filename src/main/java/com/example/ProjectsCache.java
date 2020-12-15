package com.example;

import com.example.entity.Project;
import com.example.service.ProjectService;

import java.util.List;

public class ProjectsCache {
    private static List<Project> projects;

    public static List<Project> listProjects() {
        return projects;
    }
    public static void setProjects(List<Project> ps) {
        projects = ps;
    }
}
