package com.example.web.model.mapper;

import com.example.web.model.entity.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface QuestionMapper {
    @Select("select * from question where userid = #{userid}")
    List<Question> getQuestionByUserid(Long userid);
    @Select("select * from question where courseid = #{courseid}")
    List<Question> getQuestionByCourseid(Integer courseid);
    @Select("select max(questionid) from question")
    Integer getLastQuestionId();
    @Select("select * from question where questionid = #{questionid}")
    Question getQuestionById(Integer questionid);
    @Select("select q.* from question q INNER JOIN course c on q.courseid = c.courseid where c.userid = #{userid}")
    List<Question> getQuestionByTeacher(Long userid);
    @Insert("insert into question(courseid,userid,title,content,privatecheck,answercheck,markcheck) values(#{courseid},#{userid},#{title},#{content},#{privatecheck},0,0)")
    int addQuestion(@Param("courseid") Integer courseid,@Param("userid") Long userid,@Param("title") String title,@Param("content") String content,@Param("privatecheck") Integer privatecheck);
    @Delete("delete from question where questionid = #{questionid}")
    int deleteQuestionById(Integer questionid);
    @Update("update question set answercheck = 1 where questionid = #{questionid}")
    int alterAnswerCheck(Integer questionid);
    @Update("update question set markcheck = 1 where questionid = #{questionid}")
    int alterMarkCheck(Integer questionid);
    @Update("update question set answercheck = 0,markcheck = 0 where questionid = #{questionid}")
    int deleteAnswerCheck(Integer questionid);
    @Update("update question set markcheck = 0 where questionid = #{questionid}")
    int deleteMarkCheck(Integer questionid);
    @Update("update question set title=#{title},content=#{content},privatecheck=#{privatecheck} where questionid = #{questionid}")
    int alterQuestion(@Param("questionid") Integer questionid,@Param("title") String title,@Param("content") String content,@Param("privatecheck") Integer privatecheck);
    @Select("select * from question where courseid = #{courseid} and title like #{searchecontent}")
    List<Question> getQuestionsBySearch(@Param("courseid") Integer courseid,@Param("searchecontent") String searchcontent);
}
