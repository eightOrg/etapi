package com.demo.service;

import io.vertx.core.json.JsonObject;

/**
 * Created by miaoch on 2016/8/9.
 */
public interface UserService {

    JsonObject getUserById(JsonObject params);
    JsonObject getUserList(JsonObject params);
}
