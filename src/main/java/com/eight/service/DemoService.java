package com.eight.service;

import com.eight.pojo.Demo;
import com.eight.trundle.db.pojo.Identifiable;
import io.vertx.core.json.JsonObject;

/**
 * Created by miaoch on 2016/8/9.
 */
public interface DemoService {
    JsonObject demoMethod(JsonObject params);
}
