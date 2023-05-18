package com.example.web.controller.admin;

import com.example.web.model.entity.Course;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "AdminCourseServlet", value = "/AdminCourse")
public class AdminCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        List<Course> courseList = null;
        List<String> teacherNameList = new ArrayList<>();
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper mapper = sqlSession.getMapper(CourseMapper.class);
            courseList = mapper.getCourseList();
        }
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            for(int i=0;i<courseList.size();i++){
                teacherNameList.add(mapper.getTeacherName(courseList.get(i).getUserid()));
            }
        }
        Context context = new Context();
        context.setVariable("courseList",courseList);
        context.setVariable("teacherNameList",teacherNameList);
        ThymeleafUtil.getEngine().process("templates/adminCourse.html", context, resp.getWriter());
    }

}
