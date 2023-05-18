package com.example.web.controller.teacher;

import com.example.web.model.entity.Course;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.TeachMapper;
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

@WebServlet(name = "TeacherCourseServlet", value = "/TeacherCourse")
public class TeacherCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List<Course> courseList = null;
//        List<String> teacherNameList = new ArrayList<>();
        List<Integer> courseCount = new ArrayList<>();
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeachMapper teachMapper = sqlSession.getMapper(TeachMapper.class);
//            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            courseList = courseMapper.getCoursesByUserid(user.getUserid());
            for (Course course : courseList){
                courseCount.add(teachMapper.getTeachByCourse(course.getCourseId()).size());
            }
        }
        Context context = new Context();
        context.setVariable("username",user.getUsername());
        context.setVariable("courseList",courseList);
        context.setVariable("courseCount",courseCount);
        ThymeleafUtil.getEngine().process("templates/teacherCourse.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
