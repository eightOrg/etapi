package com.eight.service.impl;

import com.eight.dao.UserDao;
import com.eight.dao.UserRegisterDao;
import com.eight.pojo.User;
import com.eight.pojo.UserRegister;
import com.eight.service.SystemService;
import com.eight.trundle.Constants;
import com.eight.trundle.crypt.MD5;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.ob.OneOb;
import com.eight.trundle.params.JsonUtil;
import com.eight.trundle.params.MapUtil;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 不需要session的一系列请求
 * 不需要继承BaseService
 * Created by miaoch on 2016/8/9.
 */
@Service("SystemService")
public class SystemServiceImpl implements SystemService {
    Logger logger = LoggerFactory.getLogger(SystemServiceImpl.class);

    @Autowired
    private UserRegisterDao userRegisterDao;
    @Autowired
    private UserDao userDao;

    @Override
    public JsonObject login(JsonObject params) {
        String password = params.getValue("password").toString();
        password = MD5.MD5(password);
        params.put("password", password);
        User user = userDao.selectOne(params.getMap());
        return user != null?JsonUtil.getTrueOb(user, "登录成功"):JsonUtil.getFaildOb("账户密码错误！");
    }

    @Override
    public JsonObject register(JsonObject params) {
        Map obj = params.getMap();
        if (obj == null) {
            return JsonUtil.getFaildOb("插入元素不能为空!");
        } else if (obj.get("mobilephone") == null){
            return JsonUtil.getFaildOb("手机号不能为空!");
        }
        UserRegister userRegister = userRegisterDao.selectById(obj.get("mobilephone").toString());
        if (userRegister != null){
            if ("1".equals(userRegister.getCode())) {
                return JsonUtil.getFaildOb("用户已经注册！");
            } else {
                int count = userRegisterDao.update(obj);
                return JsonUtil.getTrueOb(count + "个元素被更新");
            }
        } else {
            if (!params.containsKey("password")) {
                return JsonUtil.getFaildOb("必须输入密码");
            }
            String password = params.getValue("password").toString();
            password = MD5.MD5(password);
            params.put("password", password);
            if (!MapUtil.isNotEmpty(obj, Constants.POJO_STATE)) {
                obj.put(Constants.POJO_STATE, Constants.STATE_OK);
            }
            if (!obj.containsKey(Constants.POJO_CREATETIME)) {
                obj.put(Constants.POJO_CREATETIME, System.currentTimeMillis());
            }
            int count = userRegisterDao.insert(obj);
            return JsonUtil.getTrueOb(count + "个元素被插入");
        }
    }
}