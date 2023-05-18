package com.example.web.model.mapper;

import com.example.web.model.entity.Teach;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TeachMapper {
    @Insert("insert into teach(courseid,userid) values(#{courseid},#{userid})")
    int addTeach(@Param("courseid") Integer courseid,@Param("userid") Long userid);
    @Select("select courseid from teach where userid = #{userid}")
    List<Integer> getCourseIdByUserid(Long userid);
    @Select("select * from teach where courseid = #{courseid}")
    List<Teach> getTeachByCourse(Integer courseid);
    @Delete("delete from teach where courseid = #{courseid} and userid = #{userid}")
    int deleteTeach(@Param("courseid") Integer courseid, @Param("userid") Long userid);
}
