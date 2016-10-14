package com.eight.service.impl;

import com.eight.dao.UserDao;
import com.eight.pojo.User;
import com.eight.service.UserService;
import com.eight.trundle.crypt.MD5;
import com.eight.trundle.db.dao.BaseDao;
import com.eight.trundle.db.service.impl.BaseServiceImpl;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.params.JsonUtil;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
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

    @Override
    public JsonObject insert(JsonObject params) {
        if (!params.containsKey("password")) {
            return JsonUtil.getFaildOb("必须输入密码");
        }
        String password = params.getValue("password").toString();
        password = MD5.MD5(password);
        params.put("password", password);
        return super.insert(params);
    }
}