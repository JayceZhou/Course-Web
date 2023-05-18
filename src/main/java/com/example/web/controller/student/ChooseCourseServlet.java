package com.example.web.controller.student;

import com.example.web.model.entity.Course;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.StudengCheckMapper;
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
import java.util.Map;

@WebServlet(name = "ChooseCourseServlet", value = "/ChooseCourse")
public class ChooseCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
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
        context.setVariable("username",user.getUsername());
        context.setVariable("courseList",courseList);
        context.setVariable("teacherNameList",teacherNameList);
        ThymeleafUtil.getEngine().process("templates/chooseCourse.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Map<String,String[]> map = req.getParameterMap();
        if(map.containsKey("table_records")){
            String[] chooseResults = req.getParameterValues("table_records");
            try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
                TeachMapper teachMapper = sqlSession.getMapper(TeachMapper.class);
                int res = 0;
                for(int i = 0;i<chooseResults.length;i++){
                    res += teachMapper.addTeach(Integer.valueOf(chooseResults[i]), user.getUserid());
                }
                StudengCheckMapper checkMapper = sqlSession.getMapper(StudengCheckMapper.class);
                int resFlag = checkMapper.alterChooseCheck(user.getUserid());
            }
            resp.getWriter().print("<script language='javascript'>" +
                    "alert('选课完成!');" +
                    "window.location.href='StudentIndex';</script>");
            return;
        }
        resp.sendRedirect("ChooseCourse");
    }
}
