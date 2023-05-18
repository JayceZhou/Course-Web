package com.example.web.controller.student;

import com.example.web.model.entity.User;
import com.example.web.model.mapper.QuestionMapper;
import com.example.web.utils.MyBatisUtil;
import com.example.web.utils.ThymeleafUtil;
import org.apache.ibatis.session.SqlSession;
import org.thymeleaf.context.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

@WebServlet(name = "StudentQuestionServlet", value = "/StudentQuestion")
@MultipartConfig()
public class StudentQuestionServlet extends HttpServlet {

    private String getFileName(Part part){
        String fname = null;
        String header = part.getHeader("content-disposition");
        fname = header.substring(header.lastIndexOf("=")+2,header.length()-1);
        return fname;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        String teacherName = req.getParameter("teachername");
        Context context = new Context();
        context.setVariable("courseid",courseid);
        context.setVariable("username",user.getUsername());
        context.setVariable("teachername",teacherName);
        ThymeleafUtil.getEngine().process("templates/studentQuestion.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        String teacherName = req.getParameter("teachername");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Map<String,String[]> map = req.getParameterMap();
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        Integer questionId = null;
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);

            if(map.containsKey("privateCheck")){
                int res = questionMapper.addQuestion(courseid, user.getUserid(), req.getParameter("questionTitle"),req.getParameter("questionContent"),0);
            }
            else {
                int res = questionMapper.addQuestion(courseid, user.getUserid(), req.getParameter("questionTitle"),req.getParameter("questionContent"),1);
            }
            questionId = questionMapper.getLastQuestionId();
        }
        for (String key: map.keySet()){
            System.out.println("key: "+key+" , value: "+ Arrays.toString(map.get(key)));
        }
        String path = this.getServletContext().getRealPath("/");
        Part p = req.getPart("loadFile");
        System.out.println(p.getContentType());
        String message = "提交成功";
        if(p.getSize() > 3*1024*1024){
            p.delete();
            message = "文件太大，不能上传";
        }else {
            path = path + "\\question\\" + questionId;
            File f = new File(path);
            if(!f.exists()){
                f.mkdirs();
            }
            String fname = getFileName(p);
            if(fname != null && !fname.isEmpty()){
                p.write(path+"\\"+fname);
                message="文件上传成功";
            }
        }
        resp.getWriter().print("<script language='javascript'>" +
                "alert('"+message+"');" +
                "window.location.href='CourseQuestion?courseid="+courseid+"&teachername="+teacherName+"';</script>");
    }
}
