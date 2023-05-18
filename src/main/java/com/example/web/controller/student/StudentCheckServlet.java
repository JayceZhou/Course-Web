package com.example.web.controller.student;

import com.example.web.model.entity.User;
import com.example.web.model.mapper.StudengCheckMapper;
import com.example.web.utils.MyBatisUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "StudentCheckServlet", value = "/StudentCheck")
public class StudentCheckServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            StudengCheckMapper checkMapper = sqlSession.getMapper(StudengCheckMapper.class);
            int checkResult = checkMapper.checkStudentById(user.getUserid());
            if(checkResult == 0){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('还未选课，选课后可进入本系统!');" +
                        "window.location.href='ChooseCourse';</script>");
            }else {
                resp.sendRedirect("StudentIndex");
            }
        }
    }
}
