package com.jit.Other.dao.MysqlDao;

import com.jit.Other.model.UserCompare;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeviceDao {

   @Select("select * from user_device")
    public List<UserCompare> test();


    @Select("select * from user_device where userName=#{userName}")
    public List<UserCompare> show_all_device(String userName);

    @Delete("delete from user_device where userName = #{userName} and deviceId = #{deviceId}")
    public void delete_one_device(String userName,String deviceId);

    @Insert("insert into user_device (userName,deviceId) values (#{userName},#{deviceId})")
    public  void add_one_device(String userName,String deviceId);

    @Select("select deviceId from user_device where userName = #{userName} and deviceId = #{deviceId}")
    public  String judge_exist_device(String userName,String deviceId);
}
