package com.example;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.time.LocalDateTime;

@WebListener
public class DeadlineAppContext implements ServletContextListener {
    private static final LocalDateTime dl = LocalDateTime.of(2021, 12, 11, 20, 0);
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String dlStr = dl.toString().replace("T", " ").substring(0, 16);
        sce.getServletContext().setAttribute("deadline", dlStr);
    }

    public static LocalDateTime getDeadline() {
        return dl;
    }
}
