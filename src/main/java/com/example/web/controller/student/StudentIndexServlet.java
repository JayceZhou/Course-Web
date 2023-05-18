package com.example.web.controller.student;

import com.example.web.model.entity.Question;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.QuestionMapper;
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

@WebServlet(name = "StudentIndexServlet", value = "/StudentIndex")
public class StudentIndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        List <Question> questionList = null;
        List<Question> questions1 = new ArrayList<>();
        List<String> courseName1 = new ArrayList<>();
        List<Question> questions2 = new ArrayList<>();
        List<String> courseName2 = new ArrayList<>();
        List<Question> questions3 = new ArrayList<>();
        List<String> courseName3 = new ArrayList<>();
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            questionList = questionMapper.getQuestionByUserid(user.getUserid());
            for (Question question : questionList) {
                if (question.getAnswerCheck() == 0) {
                    questions3.add(question);
                    courseName3.add(courseMapper.getCourseById(question.getCourseid()).getCourseName());
                }
                if (question.getAnswerCheck() == 1 && question.getMarkCheck() == 0) {
                    questions2.add(question);
                    courseName2.add(courseMapper.getCourseById(question.getCourseid()).getCourseName());
                }
                if (question.getAnswerCheck() == 1 && question.getMarkCheck() == 1) {
                    questions1.add(question);
                    courseName1.add(courseMapper.getCourseById(question.getCourseid()).getCourseName());
                }
            }
        }
        Context context = new Context();
        context.setVariable("username",user.getUsername());
        context.setVariable("questions1",questions1);
        context.setVariable("questions2",questions2);
        context.setVariable("questions3",questions3);
        context.setVariable("courseName1",courseName1);
        context.setVariable("courseName2",courseName2);
        context.setVariable("courseName3",courseName3);
        ThymeleafUtil.getEngine().process("templates/studentIndex.html", context, resp.getWriter());
    }

}
