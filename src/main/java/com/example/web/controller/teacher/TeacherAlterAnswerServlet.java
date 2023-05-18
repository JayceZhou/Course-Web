package com.example.web.controller.teacher;

import com.example.web.model.entity.Answer;
import com.example.web.model.entity.Question;
import com.example.web.model.entity.User;
import com.example.web.model.mapper.AnswerMapper;
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

@WebServlet(name = "TeacherAlterAnswerServlet", value = "/TeacherAlterAnswer")
@MultipartConfig()
public class TeacherAlterAnswerServlet extends HttpServlet {

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
        Integer questionid = Integer.valueOf(req.getParameter("questionid"));
        Integer answerid = Integer.valueOf(req.getParameter("answerid"));
        Answer answer = null;
        Context context = new Context();
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            AnswerMapper answerMapper = sqlSession.getMapper(AnswerMapper.class);
            answer = answerMapper.getAnswerByQuestion(questionid);
        }
        context.setVariable("questionid",questionid);
        context.setVariable("username",user.getUsername());
        context.setVariable("answer",answer);
        ThymeleafUtil.getEngine().process("templates/teacherAlterAnswer.html", context, resp.getWriter());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");
        Map<String,String[]> map = req.getParameterMap();
        for (String key: map.keySet()){
            System.out.println("key: "+key+" , value: "+ Arrays.toString(map.get(key)));
        }
        Integer questionId = Integer.valueOf(req.getParameter("questionid"));
        Integer answerId = null;
        Integer courseid = null;
        Question question = null;
        try (SqlSession sqlSession = MyBatisUtil.getFactory().openSession(true)){
            AnswerMapper answerMapper = sqlSession.getMapper(AnswerMapper.class);
            QuestionMapper questionMapper = sqlSession.getMapper(QuestionMapper.class);
            answerMapper.alterAnswer(questionId,req.getParameter("answerContent"));
            int res = questionMapper.alterAnswerCheck(questionId);
            answerId = answerMapper.getLastAnswerId();
            courseid = questionMapper.getQuestionById(questionId).getCourseid();
            question = questionMapper.getQuestionById(questionId);
            questionMapper.deleteMarkCheck(questionId);
        }
        String path = this.getServletContext().getRealPath("/");
        Part p = req.getPart("loadFile");
        System.out.println(p.getContentType());
        String message = "提交成功";
        if(p.getSize() > 3*1024*1024){
            p.delete();
            message = "文件太大，不能上传";
        }else {
            path = path + "\\answer\\" + answerId;
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
                "window.location.href='TeacherQuestionDetail?questionid="+questionId+"&questionUser="+question.getUserid()+"&courseid="+question.getCourseid()+"';</script>");
    }
}
