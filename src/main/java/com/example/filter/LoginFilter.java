package com.example.filter;

import com.example.DeadlineAppContext;
import com.example.ProjectsCache;
import com.example.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter("/managerx/*")
public class LoginFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        boolean isAfter = LocalDateTime.now().isAfter(DeadlineAppContext.getDeadline());
        req.setAttribute("isAfter", isAfter);
        if (user == null || isAfter) {
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res);
            return;
        }
        chain.doFilter(req, res);
    }
}
