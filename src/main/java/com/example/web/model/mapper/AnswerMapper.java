package com.example.web.model.mapper;

import com.example.web.model.entity.Answer;
import org.apache.ibatis.annotations.*;

public interface AnswerMapper {
    @Insert("insert into answer(questionid,anscontent) value(#{questionid},#{anscontent})")
    int addAnswer(@Param("questionid") Integer questionid, @Param("anscontent") String anscontent);
    @Select("select max(answerid) from answer")
    Integer getLastAnswerId();
    @Select("select * from answer where questionid = #{questionid}")
    Answer getAnswerByQuestion(Integer questionid);
    @Delete("delete from answer where answerid = #{answerid}")
    int deleteAnswer(Integer answerid);
    @Update("update answer set anscontent = #{anscontent} where questionid = #{questionid}")
    int alterAnswer(@Param("questionid") Integer questionid,@Param("anscontent") String content);
}
