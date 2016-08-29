package com.eight.service.impl;

import com.eight.dao.DemoDao;
import com.eight.dao.UserDao;
import com.eight.pojo.Demo;
import com.eight.pojo.User;
import com.eight.service.DemoService;
import com.eight.service.UserService;
import com.eight.trundle.db.dao.BaseDao;
import com.eight.trundle.db.service.impl.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by miaoch on 2016/8/9.
 */
@Service("UserService")
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    protected BaseDao<User> getBaseDao() {
        return (BaseDao<User>) userDao;
    }

}