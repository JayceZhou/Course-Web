package com.example.web.controller.teacher;

import com.example.web.model.entity.Course;
import com.example.web.model.entity.Question;
import com.example.web.model.entity.Teach;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.QuestionMapper;
import com.example.web.model.mapper.TeachMapper;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TeacherCourseQuestionServlet", value = "/TeacherCourseQuestion")
public class TeacherCourseQuestionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Integer courseId = Integer.valueOf(req.getParameter("courseid"));
        List<User> userList = new ArrayList<>();
        Course course = null;
        List<Question> questions = null;
        List<String > questionNameList = new ArrayList<>();
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            TeachMapper teachMapper = sqlSession.getMapper(TeachMapper.class);
            List<Teach> teachList = teachMapper.getTeachByCourse(courseId);
            for (Teach teach :teachList){
                userList.add(userMapper.checkUserByRegister(teach.getUserid()));
            }
            course = courseMapper.getCourseById(courseId);
            questions = questionMapper.getQuestionByCourseid(courseId);
            for(Question question : questions){
                questionNameList.add(userMapper.checkUserByRegister(question.getUserid()).getUsername());
            }
        }
        Context context = new Context();
        context.setVariable("username",user.getUsername());
        context.setVariable("userList",userList);
        context.setVariable("course",course);
        context.setVariable("questionList",questions);
        context.setVariable("questionNameList",questionNameList);
        ThymeleafUtil.getEngine().process("templates/teacherCourseQuestion.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
