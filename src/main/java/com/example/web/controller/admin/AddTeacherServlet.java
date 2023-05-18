package com.example.web.controller.admin;

import com.example.web.model.entity.Teacher;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.TeacherMapper;
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
import java.io.IOException;

@WebServlet(name = "AddTeacherServlet", value = "/AddTeacher")
public class AddTeacherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Context context = new Context();
        ThymeleafUtil.getEngine().process("templates/adminAddTeacher.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Long userid = Long.valueOf(req.getParameter("userid"));
        String password = req.getParameter("password");
        String username = req.getParameter("teacherName");
        String title = req.getParameter("teacherTitle");
        String teacherInf = req.getParameter("teacherInf");
        User user = new User(userid,username,password,2);
        Teacher teacher = new Teacher(userid,username,title,teacherInf);
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            int res1 = userMapper.addStudentAccount(user);
            int res2 = teacherMapper.addTeacher(teacher);
            if(res1 == res2 && res1 == 1){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('添加成功！');"+
                        "window.location.href='AddTeacher';</script>");
            }else {
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('添加失败！');" +
                        "window.location.href='AddTeacher';</script>");
            }
        }
    }
}
