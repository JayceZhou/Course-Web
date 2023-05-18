package com.example.web.controller;

import com.example.web.utils.ThymeleafUtil;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "LogOutServlet", value = "/LogOut")
public class LogOutServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        HttpSession httpSession = req.getSession();
        httpSession.invalidate();
        Context context = new Context();
        ThymeleafUtil.getEngine().process("templates/login.html", context, resp.getWriter());
    }
}
