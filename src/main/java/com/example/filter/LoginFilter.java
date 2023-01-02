package com.example.filter;

import com.example.entity.User;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@WebFilter("/managerx/*")
public class LoginFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User) req.getSession().getAttribute("user");
        LocalDateTime dl = (LocalDateTime) req.getServletContext().getAttribute("deadlineT");
        boolean isAfter = LocalDateTime.now().isAfter(dl);
        req.setAttribute("isAfter", isAfter);
        if (user == null || isAfter) {
            req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, res);
            return;
        }
        chain.doFilter(req, res);
    }
}
