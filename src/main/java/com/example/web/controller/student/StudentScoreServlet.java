package com.example.web.controller.student;

import com.example.web.model.entity.Course;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.QuestionMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "StudentScoreServlet", value = "/StudentScore")
public class StudentScoreServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Integer questionid = Integer.valueOf(req.getParameter("questionid"));
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        int coursescore = Integer.parseInt(req.getParameter("coursescore"));
        Course course = null;
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            course = courseMapper.getCourseById(courseid);
            if(course.getCourseScore() == null){
                Double d = (double) coursescore;
                courseMapper.alterCourseScore(d,courseid);
            }else {
                Double d = (course.getCourseScore() + coursescore)/2;
                courseMapper.alterCourseScore(d,courseid);
            }
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
            questionMapper.alterMarkCheck(questionid);
        }
        resp.sendRedirect("StudentIndex");
    }
}
