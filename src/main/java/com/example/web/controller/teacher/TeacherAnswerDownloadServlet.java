package com.example.web.controller.teacher;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(name = "TeacherAnswerDownloadServlet", value = "/TeacherAnswerDownload")
public class TeacherAnswerDownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        Long questionUser = Long.valueOf(req.getParameter("questionUser"));
        Integer courseid = Integer.valueOf(req.getParameter("courseid"));
        Integer questionid = Integer.valueOf(req.getParameter("questionid"));
        Integer answerid = Integer.valueOf(req.getParameter("answerid"));
//        System.out.println(questionid);
        String dataDirectory = req.getServletContext().getRealPath("/answer/"+answerid);
        File file = new File(dataDirectory);
        if(!file.exists()){
            System.out.println("找不到文件");
            resp.setContentType("text/html;charset=utf-8");
            resp.sendRedirect("TeacherQuestionDetail?questionid="+questionid+"&questionUser="+questionUser+"&courseid="+courseid);
        }else {
            File result[] = file.listFiles();
            if(result[0].isFile()){
                System.out.println(result[0].getName());
                resp.setContentType("application/octet-stream");
                resp.addHeader("Content-Disposition","attachment;filename="+result[0].getName());
                FileInputStream fileInputStream = new FileInputStream(result[0]);
                ServletOutputStream outputStream = resp.getOutputStream();
                //返回给浏览器
                int len = 0;
                byte[] buffer = new byte[1024];
                while ((len = fileInputStream.read(buffer))!=-1){
                    outputStream.write(buffer,0,len);
                }
                fileInputStream.close();
                outputStream.close();
            }
        }
    }
}
