package com.jit.Other.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface UserMapper {

    //登录
    @Select("select password from user_info where name = #{name}")
    String findPasswordByName(@Param("name") String name);

    //注册
    @Insert("insert into user_info (name,password,regtime) "
            + "values (#{name}, #{password}, now())")
    int insertNewUser(@Param("name") String name, @Param("password") String password);

    @Insert("insert into contact_info (name,email,email_ctl) values (#{name}, #{email}, \"on\")")
    int insertNewUserEmail(@Param("name") String name, @Param("email") String email);

    //修改密码(通过用户名)
    @Update("update user_info set password=#{password} where name=#{name}")
    int updatePasswordByName(@Param("name") String name, @Param("password") String password);

    @Select("select name from user_info where name = #{name}")
    String ExistUser(@Param("name") String name);

    @Select("select name from contact_info where email = #{email}")
    String ExistEmail(@Param("email") String email);



}
