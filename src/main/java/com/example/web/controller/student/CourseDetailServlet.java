package com.example.web.controller.student;

import com.example.web.model.entity.Course;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.CourseMapper;
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

@WebServlet(name = "CourseDetailServlet", value = "/CourseDetail")
public class CourseDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        String teachername = req.getParameter("teachername");
        Course course = new Course();
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            course = courseMapper.getCourseById(courseid);
        }
        Context context = new Context();
        context.setVariable("username",user.getUsername());
        context.setVariable("course",course);
        context.setVariable("teachername",teachername);
        ThymeleafUtil.getEngine().process("templates/courseDetail.html", context, resp.getWriter());
    }
}
