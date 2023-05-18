package com.example.web.model.mapper;

import com.example.web.model.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface UserMapper {
    @Select("select * from account")
    List<User> getUserList();
    @Select("Select * from account where userid = #{userid} and password = #{password}")
    User getUserByLogin(@Param("userid") Long userid,@Param("password") String password);
    @Select("Select * from account where userid = #{userid}")
    User checkUserByRegister(Long userid);
    @Insert("insert into account(userid,username,password,level) values(#{userid},#{username},#{password},#{level})")
    int addStudentAccount(User user);
    @Delete("delete from account where userid = #{userid}")
    int deleteUserById(Long userid);
    @Update("update account set password = #{newpwd} where userid = #{userid}")
    int changePasswordById(@Param("userid") Long userid,@Param("newpwd") String newpwd);
}
