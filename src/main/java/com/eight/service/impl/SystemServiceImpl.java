package com.eight.service.impl;

import com.eight.dao.SystemDao;
import com.eight.dao.UserDao;
import com.eight.pojo.User;
import com.eight.service.SystemService;
import com.eight.trundle.crypt.MD5;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.ob.OneOb;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 不需要session的一系列请求
 * 不需要继承BaseService
 * Created by miaoch on 2016/8/9.
 */
@Service("SystemService")
public class SystemServiceImpl implements SystemService {
    Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private SystemDao systemDao;
    @Autowired
    private UserDao userDao;

    @Override
    public JsonObject login(JsonObject params) {
        String password = params.getValue("password").toString();
        password = MD5.MD5(password);
        params.put("password", password);
        User user = userDao.selectOne(params.getMap());
        return user != null?new JsonObject(Json.encode(new OneOb<User>().setOb(user).setMsg("登录成功"))):
                new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg("账户密码错误！")));
    }
}