package com.example.web.model.mapper;

import com.example.web.model.entity.Course;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CourseMapper {
    @Select("select * from course where userid = #{userid}")
    List<Course> getCoursesByUserid(Long userid);
    @Select("select * from course")
    List<Course> getCourseList();
    @Select("select * from course where courseid = #{courseid}")
    Course getCourseById(Integer courseid);
    @Select("select c.* FROM course c INNER JOIN teach t ON t.userid = #{userid} and c.courseid = t.courseid left JOIN account a on a.userid = c.userid where c.coursename like #{searchcontent} or a.username like #{searchcontent}")
    List<Course> getCourseBySearch(@Param("userid") Long userid,@Param("searchcontent") String searchContent);
    @Insert("insert into course(coursename,userid,courseinf) values(#{coursename},#{userid},#{courseinf})")
    int addCourse(@Param("coursename") String courseName,@Param("userid") Long userid,@Param("courseinf") String courseInf);
    @Update("update course set coursename = #{courseName},userid = #{userid},courseinf = #{courseInf} where courseid = #{courseId}")
    int alterCourse(Course course);
    @Update("update course set coursescore = #{coursescore} where courseid = #{courseid}")
    int alterCourseScore(@Param("coursescore") Double coursescore, @Param("courseid") Integer courseid);
    @Delete("delete from course where courseid = #{courseId}")
    int deleteCourse(Integer courseId);
}
