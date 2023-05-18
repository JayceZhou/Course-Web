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

@WebServlet(name = "AlterTeacherServlet", value = "/AlterTeacher")
public class AlterTeacherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Long userid = Long.valueOf(req.getParameter("userid"));
        Teacher teacher = null;
        try(SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            teacher = mapper.getTeacherByUserid(userid);
        }
        Context context = new Context();
        context.setVariable("teacher",teacher);
        ThymeleafUtil.getEngine().process("templates/adminAlterTeacher.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        Long userid = Long.valueOf(req.getParameter("userid"));
        String teacherName = req.getParameter("teacherName");
        String teacherTitle = req.getParameter("teacherTitle");
        String teacherInf = req.getParameter("teacherInf");
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
            int res = mapper.alterTeacher(new Teacher(userid,teacherName,teacherTitle,teacherInf));
            if(res != 1){
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('修改失败，请重新尝试！');</script>");
            } else{
                resp.getWriter().print("<script language='javascript'>" +
                        "alert('修改成功！');" +
                        "window.location.href='AdminIndex';</script>");
            }
        }
    }
}
