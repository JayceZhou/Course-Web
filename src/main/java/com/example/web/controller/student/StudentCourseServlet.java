package com.example.web.controller.student;

import com.example.web.model.entity.Course;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.TeachMapper;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "StudentCourseServlet", value = "/StudentCourse")
public class StudentCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Integer> courseIdList = null;
        List<Course> courseList = new ArrayList<>();
        List<String> teacherNameList = new ArrayList<>();
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeachMapper teachMapper = sqlSession.getMapper(TeachMapper.class);
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            courseIdList = teachMapper.getCourseIdByUserid(user.getUserid());
            for (Integer courseid: courseIdList){
                courseList.add(courseMapper.getCourseById(courseid));
            }
            for (Course course: courseList){
                teacherNameList.add(mapper.getTeacherName(course.getUserid()));
            }
        }
        Context context = new Context();
        context.setVariable("username",user.getUsername());
        context.setVariable("courseList",courseList);
        context.setVariable("teacherNameList",teacherNameList);
        ThymeleafUtil.getEngine().process("templates/studentCourse.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        String searchContent = "%"+req.getParameter("searchContent")+"%";
        List<Course> courseList = new ArrayList<>();
        List<String> teacherNameList = new ArrayList<>();
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            courseList = courseMapper.getCourseBySearch(user.getUserid(), searchContent);
            for (Course course: courseList){
                teacherNameList.add(mapper.getTeacherName(course.getUserid()));
            }
        }
        Context context = new Context();
        context.setVariable("username",user.getUsername());
        context.setVariable("courseList",courseList);
        context.setVariable("teacherNameList",teacherNameList);
        ThymeleafUtil.getEngine().process("templates/studentCourse.html", context, resp.getWriter());
    }
}
