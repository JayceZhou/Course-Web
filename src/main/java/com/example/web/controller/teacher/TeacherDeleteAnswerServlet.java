package com.example.web.controller.teacher;

import com.example.web.model.mapper.AnswerMapper;
import com.example.web.model.mapper.QuestionMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TeacherDeleteAnswerServlet", value = "/TeacherDeleteAnswer")
public class TeacherDeleteAnswerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        Integer questionid = Integer.valueOf(req.getParameter("questionid"));
        Integer answerid = Integer.valueOf(req.getParameter("answerid"));
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
            questionMapper.deleteAnswerCheck(questionid);
            AnswerMapper answerMapper = sqlSession.getMapper(AnswerMapper.class);
            answerMapper.deleteAnswer(answerid);
        }
        resp.sendRedirect("TeacherCourseQuestion?courseid="+courseid);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
