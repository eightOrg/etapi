package com.eight.service;

import com.eight.pojo.Demo;
import com.eight.trundle.db.pojo.Identifiable;
import com.eight.trundle.db.service.BaseService;
import io.vertx.core.json.JsonObject;

/**
 * Created by miaoch on 2016/8/9.
 */
public interface DemoService extends BaseService<Demo> {
    JsonObject demoMethod(JsonObject params);
    JsonObject blockingMethod(JsonObject params);
    JsonObject login(JsonObject params);
}
