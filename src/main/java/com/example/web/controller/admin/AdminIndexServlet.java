package com.example.web.controller.admin;

import com.example.web.model.entity.Teacher;
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
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminIndexServlet", value = "/AdminIndex")
public class AdminIndexServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        List <Teacher> teacherList = null;
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            teacherList = mapper.getTeacherList();
        }
        Context context = new Context();
        context.setVariable("teacherList",teacherList);
        ThymeleafUtil.getEngine().process("templates/adminTeacher.html", context, resp.getWriter());
    }

}
