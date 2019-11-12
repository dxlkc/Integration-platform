package com.jit.Other.service.Impl;

import com.jit.Other.dao.MysqlDao.DeviceDao;
import com.jit.Other.service.MysqlServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public  class MysqlServiceImpl implements MysqlServiceInterface {
    @Resource
    DeviceDao deviceDao;

    @Override
    public List show_all_device(String userName) {
       return deviceDao.show_all_device(userName);
    }

    @Override
    public String test() {
        System.out.println(deviceDao.test());
       return  deviceDao.test().toString();
    }
}
