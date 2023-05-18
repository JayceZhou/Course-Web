package com.example.web.controller;

import com.example.web.model.entity.User;
import com.example.web.model.mapper.UserMapper;
import com.example.web.utils.MyBatisUtil;
import com.example.web.utils.ThymeleafUtil;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePassword")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Context context = new Context();
        ThymeleafUtil.getEngine().process("templates/changePassword.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        String nowpwd = req.getParameter("nowpwd");
        String newpwd = req.getParameter("newpwd");
        String confirm = req.getParameter("confirm");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        if(user.getPassword().equals(nowpwd)){
            if(newpwd.equals(confirm)){
                try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
                    UserMapper mapper = sqlSession.getMapper(UserMapper.class);
                    int res = mapper.changePasswordById(user.getUserid(),newpwd);
                    if(res == 1){
                        resp.getWriter().print("<script language='javascript'>" +
                                "alert('修改成功，请重新登录！');" +
                                "window.location.href='LogOut';</script>");
                        return;
                    }
                }
            }
            resp.getWriter().print("<script language='javascript'>" +
                    "alert('两遍密码输入不一致，修改失败!');" +
                    "window.location.href='ChangePassword';</script>");
            return;
        }
        resp.getWriter().print("<script language='javascript'>" +
                "alert('原密码输入错误，修改失败！');" +
                "window.location.href='ChangePassword';</script>");
    }
}
