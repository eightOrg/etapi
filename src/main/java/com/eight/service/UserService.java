package com.eight.service;

import com.eight.trundle.db.service.BaseService;
import io.vertx.core.json.JsonObject;

/**
 * Created by miaoch on 2016/8/9.
 */
public interface UserService extends BaseService {

    /**
     * 添加好友
     * @param params
     * @return
     */
    JsonObject addFriend(JsonObject params);
}
