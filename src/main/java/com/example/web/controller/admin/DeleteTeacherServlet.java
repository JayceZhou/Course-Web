package com.example.web.controller.admin;

import com.example.web.model.entity.Course;
import com.example.web.model.mapper.CourseMapper;
import com.example.web.model.mapper.TeacherMapper;
import com.example.web.model.mapper.UserMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "DeleteTeacherServlet", value = "/DeleteTeacher")
public class DeleteTeacherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Long userid = Long.valueOf(req.getParameter("userid"));
//        System.out.println(userid);
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            CourseMapper courseMapper = sqlSession.getMapper(CourseMapper.class);
            List<Course> courseList = courseMapper.getCoursesByUserid(userid);
//            System.out.println(courseList);
            if(courseList.size() != 0){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('删除失败，该教师还有课程教授！');" +
                        "window.location.href='AdminIndex';</script>");
                return;
            }
        }
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeacherMapper teacherMapper = sqlSession.getMapper(TeacherMapper.class);
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            int res1 = teacherMapper.deleteTeacherById(userid);
            int res2 = userMapper.deleteUserById(userid);
            if(res1 != 0 && res2 != 0){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('删除成功！');" +
                        "window.location.href='AdminIndex';</script>");
            }
        }
    }

}
