package com.example.web.controller;

import com.example.web.model.entity.User;
import com.example.web.model.mapper.StudengCheckMapper;
import com.example.web.model.mapper.UserMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "RegisterServlet", value = "/Register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        Map<String, String[]> map = req.getParameterMap();
        String username = req.getParameter("username");
        Long sid = Long.valueOf(req.getParameter("sid"));
        String password = req.getParameter("password");
        User user = null;
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true )){
            UserMapper mapper = sqlSession.getMapper(UserMapper.class);
            StudengCheckMapper checkMapper = sqlSession.getMapper(StudengCheckMapper.class);
            user = mapper.checkUserByRegister(sid);
            if(user != null){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('您输入的账号账号已注册，请直接登录！');" +
                        "window.location.href='Login';</script>");
                return;
            }
            int insertResult = mapper.addStudentAccount(new User(sid,username,password,3));
            if(insertResult == 0){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('账号注册失败，数据库异常!');" +
                        "window.location.href='Login';</script>");
            }else {
                int res = checkMapper.addCheckStudent(sid,0);
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('注 册 成 功 !');" +
                        "window.location.href='Login';</script>");
            }
        }
    }
}
