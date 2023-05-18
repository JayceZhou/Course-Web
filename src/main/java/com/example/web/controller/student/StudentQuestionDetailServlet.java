package com.example.web.controller.student;

import com.example.web.model.entity.Answer;
import com.example.web.model.entity.Question;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.AnswerMapper;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.QuestionMapper;
import com.example.web.model.mapper.UserMapper;
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

@WebServlet(name = "StudentQuestionDetailServlet", value = "/StudentQuestionDetail")
public class StudentQuestionDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
//        System.out.println(req.getParameter("questionid"));
        Integer questionid = Integer.valueOf(req.getParameter("questionid"));
        Long questionUser = Long.valueOf(req.getParameter("questionUser"));
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        Question question = new Question();
        Context context = new Context();
        context.setVariable("username",user.getUsername());
        if(questionUser.equals(user.getUserid())) {
            context.setVariable("ownercheck",1);
        }else {
            context.setVariable("ownercheck",0);
        }
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            AnswerMapper answerMapper = sqlSession.getMapper(AnswerMapper.class);
            question = questionMapper.getQuestionById(questionid);
            context.setVariable("teachername",userMapper.checkUserByRegister(courseMapper.getCourseById(courseid).getUserid()).getUsername());
            context.setVariable("question",question);
            context.setVariable("course",courseMapper.getCourseById(courseid));
            context.setVariable("questionUserName",userMapper.checkUserByRegister(questionUser).getUsername());
//            context.setVariable();
            if(question.getAnswerCheck().equals(1)){
                Answer answer = answerMapper.getAnswerByQuestion(questionid);
                context.setVariable("answer",answer);
                ThymeleafUtil.getEngine().process("templates/studentQuestionDetail.html", context, resp.getWriter());
            }else {
                ThymeleafUtil.getEngine().process("templates/studentQuestionDetailNoAnswer.html", context, resp.getWriter());
            }
        }
    }

}
