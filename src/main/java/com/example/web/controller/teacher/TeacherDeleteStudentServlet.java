package com.example.web.controller.teacher;

import com.example.web.model.mapper.TeachMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "TeacherDeleteStudentServlet", value = "/TeacherDeleteStudent")
public class TeacherDeleteStudentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Long sid = Long.valueOf(req.getParameter("studentid"));
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeachMapper teachMapper = sqlSession.getMapper(TeachMapper.class);
            int res = teachMapper.deleteTeach(courseid,sid);
        }
        resp.sendRedirect("TeacherCourseQuestion?courseid="+courseid);
    }
}
