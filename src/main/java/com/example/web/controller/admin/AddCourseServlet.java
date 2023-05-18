package com.example.web.controller.admin;

import com.example.web.model.entity.Teacher;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.TeacherMapper;
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
import java.util.List;

@WebServlet(name = "AddCourseServlet", value = "/AddCourse")
public class AddCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        List<Teacher> teacherList = null;
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            teacherList = mapper.getTeacherList();
        }
        Context context = new Context();
        context.setVariable("teacherList",teacherList);
        ThymeleafUtil.getEngine().process("templates/adminAddCourse.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        String courseName = req.getParameter("courseName");
        Long userid = Long.valueOf(req.getParameter("userid"));
        String courseInf = req.getParameter("courseInf");
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            int res = mapper.addCourse(courseName,userid,courseInf);
            if(res == 1){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('添加成功！');"+
                        "window.location.href='AddCourse';</script>");
            }else {
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('添加失败！');" +
                        "window.location.href='AddCourse';</script>");
            }
        }
    }
}
