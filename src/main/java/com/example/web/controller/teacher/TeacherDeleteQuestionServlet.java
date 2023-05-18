package com.example.web.controller.teacher;

import com.example.web.model.mapper.QuestionMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TeacherDeleteQuestionServlet", value = "/TeacherDeleteQuestion")
public class TeacherDeleteQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        Integer questionid = Integer.valueOf(req.getParameter("questionid"));
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
            int res = questionMapper.deleteQuestionById(questionid);
        }
        resp.sendRedirect("TeacherCourseQuestion?courseid="+courseid);
    }
}
