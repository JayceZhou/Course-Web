package com.example.web.model.mapper;

import com.example.web.model.entity.Teacher;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface TeacherMapper {
    @Select("select * from teacher")
    List<Teacher> getTeacherList();
    @Select("select * from teacher where userid = #{userid}")
    Teacher getTeacherByUserid(Long userid);
    @Select("select teachername from teacher where userid = #{userid}")
    String getTeacherName(Long userid);
    @Delete("delete from teacher where userid = #{userid}")
    int deleteTeacherById(Long userid);
    @Update("update teacher set teachername = #{teacherName},teachertitle = #{teacherTitle},teacherinf = #{teacherInf} where userid = #{userid}")
    int alterTeacher(Teacher teacher);
    @Insert("insert into teacher(userid,teachername,teachertitle,teacherinf) values(#{userid},#{teacherName},#{teacherTitle},#{teacherInf})")
    int addTeacher(Teacher teacher);
}
