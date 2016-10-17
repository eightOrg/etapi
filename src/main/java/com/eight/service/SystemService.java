package com.eight.service;

import io.vertx.core.json.JsonObject;

/**
 * 不需要session的一系列请求
 * 不需要继承BaseService
 * Created by miaoch on 2016/8/9.
 */
public interface SystemService {

    /**
     * 登录
     * @param params
     * @return
     */
    JsonObject login(JsonObject params);

    /**
     * 获取验证码
     * @param params
     * @return
     */
    JsonObject getCode(JsonObject params);

    /**
     * 验证验证码
     * @param params
     * @return
     */
    JsonObject checkCode(JsonObject params);

    /**
     * 验证码验证成功后修改个人信息
     * @param params
     * @return
     */
    JsonObject updateOrInsertUser(JsonObject params);
}
