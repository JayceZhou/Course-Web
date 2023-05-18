package com.example.web.model.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface StudengCheckMapper {
    @Insert("insert into checkstudent(userid,flag) values(#{userid},#{flag})")
    int addCheckStudent(@Param("userid") Long userid,@Param("flag") Integer flag);
    @Select("select flag from checkstudent where userid = #{userid}")
    int checkStudentById(Long userid);
    @Update("update checkstudent set flag = 1 where userid = #{userid}")
    int alterChooseCheck(Long userid);
}
