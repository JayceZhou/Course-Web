package com.example.web.filter;

import com.example.web.model.entity.User;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/*")
public class CheckFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        String url = req.getRequestURL().toString();
        if(!url.endsWith(".js") && !url.endsWith(".css") && !url.endsWith(".html")){
            HttpSession session = req.getSession();
            User user = (User) session.getAttribute("user");
            if(user == null && !url.endsWith("Login" )&& !url.endsWith("Register")){
                res.sendRedirect("Login");
                return;
            }
        }
        chain.doFilter(req,res);
    }
}