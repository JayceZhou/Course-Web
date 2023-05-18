package com.example.web.controller.admin;

import com.example.web.model.mapper.CourseMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteCourseServlet", value = "/DeleteCourse")
public class DeleteCourseServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Integer courseId = Integer.valueOf(req.getParameter("courseid"));
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            int res = courseMapper.deleteCourse(courseId);
            if(res != 0){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('删除成功！');" +
                        "window.location.href='AdminCourse';</script>");
            }
        }
    }

}
