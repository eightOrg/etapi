package com.eight.service;

import io.vertx.core.json.JsonObject;

/**
 * 不需要session的一系列请求
 * 不需要继承BaseService
 * Created by miaoch on 2016/8/9.
 */
public interface SystemService {
    JsonObject login(JsonObject params);
}
