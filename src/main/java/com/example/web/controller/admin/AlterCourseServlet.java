package com.example.web.controller.admin;

import com.example.web.model.entity.Course;
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

@WebServlet(name = "AlterCourseServlet", value = "/AlterCourse")
public class AlterCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        Course course = null;
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            course = mapper.getCourseById(courseid);
        }
        List<Teacher> teacherList = null;
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            teacherList = mapper.getTeacherList();
        }
        Context context = new Context();
        context.setVariable("course",course);
        context.setVariable("teacherList",teacherList);
        ThymeleafUtil.getEngine().process("templates/adminAlterCourse.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        String courseName = req.getParameter("courseName");
        Long userid = Long.valueOf(req.getParameter("userid"));
        String courseInf = req.getParameter("courseInf");
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            int res = mapper.alterCourse(new Course().setCourseId(courseid).setCourseName(courseName).setUserid(userid).setCourseInf(courseInf));
            if(res == 1){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('修改成功！');"+
                        "window.location.href='AdminCourse';</script>");
            }else {
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('修改失败！');</script>");
            }
        }
    }
}
