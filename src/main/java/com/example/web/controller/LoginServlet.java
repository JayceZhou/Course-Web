package com.example.web.controller;

import com.example.web.model.entity.User;
import com.example.web.model.mapper.UserMapper;
import com.example.web.utils.MyBatisUtil;
import com.example.web.utils.ThymeleafUtil;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {
//    SqlSessionFactory factory;
//
//    @SneakyThrows
//    @Override
//    public void init() throws ServletException {
//        factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
//    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        Cookie[] cookies = req.getCookies();
        if (cookies != null) {
            Long userid = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("userid")) userid = Long.valueOf(cookie.getValue());
                if (cookie.getName().equals("password")) password = cookie.getValue();
            }
            if (userid != null && password != null) {
                //登陆校验
                try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)) {
                    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                    User user = mapper.getUserByLogin(userid, password);
                    if (user != null) {
                        HttpSession httpSession = req.getSession();
                        httpSession.setAttribute("user",user);
                        if(user.getLevel() == 1){
                            resp.sendRedirect("AdminIndex");
                        }else if(user.getLevel() == 2){
                            resp.sendRedirect("TeacherIndex");
                        }else {
                            resp.sendRedirect("StudentCheck");
                        }
                        return;   //直接返回
                    }
                }
            }
        }
        Context context = new Context();
        ThymeleafUtil.getEngine().process("templates/login.html", context, resp.getWriter());
//        resp.sendRedirect("templates/login.html");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        Map<String, String[]> map = req.getParameterMap();
        Long userid = Long.valueOf(req.getParameter("userid"));
        String password = req.getParameter("password");
        User user = null;
        try (SqlSession session = MyBatisUtil.getFactory().openSession(true)) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            user = mapper.getUserByLogin(userid, password);
            if (user != null) {
                if(map.containsKey("rememberMe")){
                    Cookie cookieUserid = new Cookie("userid",userid.toString());
                    cookieUserid.setMaxAge(60);
                    Cookie cookiePassword = new Cookie("password",password);
                    cookiePassword.setMaxAge(60);
                    resp.addCookie(cookieUserid);
                    resp.addCookie(cookiePassword);
                }
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("user",user);
                if(user.getLevel() == 1){
                    resp.sendRedirect("AdminIndex");
                }else if(user.getLevel() == 2){
                    resp.sendRedirect("TeacherIndex");
                }else {
                    resp.sendRedirect("StudentCheck");
                }
            }
            else {
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('密码错误，请重新登录!');" +
                        "window.location.href='Login';</script>");
            }
        }
    }
}
