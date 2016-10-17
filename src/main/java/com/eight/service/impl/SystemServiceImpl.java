package com.eight.service.impl;

import com.eight.dao.UserDao;
import com.eight.dao.UserRegisterDao;
import com.eight.pojo.User;
import com.eight.pojo.UserRegister;
import com.eight.service.SystemService;
import com.eight.trundle.Constants;
import com.eight.trundle.common.StringUtils;
import com.eight.trundle.crypt.MD5;
import com.eight.trundle.params.JsonUtil;
import com.eight.trundle.params.MapUtil;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

    /**
     * 获取验证码
     * @param params getType 1 忘记密码 2 新用户注册
     * @return
     */
    @Override
    public JsonObject getCode(JsonObject params) {
        if (params.containsKey("mobilephone") && params.containsKey("getType")) {
            String mobilephone = params.getValue("mobilephone").toString();
            String getType = params.getValue("getType").toString();
            String code = StringUtils.getCode(6);
            if ("1".equals(getType)) { //忘记密码
                User user = userDao.selectOne(params.getMap());
                if (user == null) {
                    return JsonUtil.getFaildOb("用户不存在!");
                }
                user.setCode(code);
                userDao.update(user.parseMap());

                //发短信接口调用
                //暂缺
            } else if ("2".equals(getType)) { //新用户注册
                if (mobilephone.equals("")) {
                    return JsonUtil.getFaildOb("手机号不能为空!");
                }
                UserRegister userRegister = userRegisterDao.selectById(params.getValue("mobilephone").toString());
                if (userRegister != null){
                    if ("1".equals(userRegister.getCode()) && userDao.selectOne(params.getMap())!=null) {
                        return JsonUtil.getFaildOb("用户已经注册！");
                    } else {
                        userRegister.setCode(code);
                        int count = userRegisterDao.update(userRegister.parseMap());
                        //发短信接口调用
                        //暂缺
                    }
                } else {
                    Map obj = params.getMap();
                    if (!MapUtil.isNotEmpty(obj, Constants.POJO_STATE)) {
                        obj.put(Constants.POJO_STATE, Constants.STATE_OK);
                    }
                    if (!obj.containsKey(Constants.POJO_CREATETIME)) {
                        obj.put(Constants.POJO_CREATETIME, System.currentTimeMillis());
                    }
                    int count = userRegisterDao.insert(obj);
                    //发短信接口调用
                    //暂缺
                }
            }
            return JsonUtil.getTrueOb();
        } else {
            return JsonUtil.getFaildOb("手机号和类型不能为空");
        }
    }

    /**
     * 验证码验证
     * @param params getType 1 忘记密码 2 新用户注册
     * @return
     */
    @Override
    public JsonObject checkCode(JsonObject params) {
        if (params.containsKey("getType")) {
            String getType = params.getValue("getType").toString();
            if ("1".equals(getType)) { //忘记密码
                return userDao.selectOne(params.getMap()) == null ?
                        JsonUtil.getFaildOb():JsonUtil.getTrueOb();
            } else if ("2".equals(getType)) { //新用户注册
                return userRegisterDao.selectOne(params.getMap()) == null ?
                        JsonUtil.getFaildOb():JsonUtil.getTrueOb();
            } else {
                return JsonUtil.getFaildOb("getType有误");
            }
        } else {
            return JsonUtil.getFaildOb("验证类型不能为空");
        }
    }

    /**
     * 更新或插入用户信息 用于注册和忘记密码
     * @param params getType 1 忘记密码 2 新用户注册
     * @return
     */
    @Override
    public JsonObject updateOrInsertUser(JsonObject params) {
        if (!params.containsKey("password")) {
            return JsonUtil.getFaildOb("密码不能为空！");
        }
        Map condition = new HashMap();
        condition.put("mobilephone", params.getString("mobilephone"));
        String password = params.getValue("password").toString();
        password = MD5.MD5(password);
        params.put("password", password);
        User user = userDao.selectOne(condition);
        if (params.containsKey("getType")) {
            String getType = params.getValue("getType").toString();
            if ("1".equals(getType)) { //更新密码
                user.setPassword(password);
                int count = userDao.update(user.parseMap());
                return JsonUtil.getTrueOb(count + "个元素被更新");
            } else if ("2".equals(getType)) { //更新或插入整个用户
                if (user == null) {
                    int count = userDao.insert(params.getMap());
                    return JsonUtil.getTrueOb(count + "个元素被插入");
                } else {
                    params.put("id", user.getId());
                    int count = userDao.update(params.getMap());
                    return JsonUtil.getTrueOb(count + "个元素被更新");
                }
            } else {
                return JsonUtil.getFaildOb("getType有误");
            }
        } else {
            return JsonUtil.getFaildOb("验证类型不能为空");
        }

    }
}