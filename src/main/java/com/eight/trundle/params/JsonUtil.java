package com.eight.trundle.params;

import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.ob.OneOb;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;

/**
 * Created by Administrator on 2016/10/14.
 */
public class JsonUtil {

    /**
     * 获取失败Json模板
     * @return
     */
    public static JsonObject getFaildOb() {
        return getFaildOb("请求失败");
    }

    /**
     * 获取失败Json模板
     * @param msg
     * @return
     */
    public static JsonObject getFaildOb(String msg) {
        return new JsonObject(Json.encode(BaseOb.getFaildOb().setMsg(msg)));
    }

    /**
     * 获取成功Json模板
     * @return
     */
    public static JsonObject getTrueOb() {
        return getTrueOb("请求成功");
    }

    /**
     * 获取成功Json模板
     * @param msg
     * @return
     */
    public static JsonObject getTrueOb(String msg) {
        return new JsonObject(Json.encode(new BaseOb().setMsg(msg)));
    }

    /**
     * 获取成功Json模板
     * @return
     */
    public static JsonObject getTrueOb(Object obj) {
        return new JsonObject(Json.encode(new OneOb<>().setOb(obj)));
    }

    /**
     * 获取成功Json模板
     * @return
     */
    public static JsonObject getTrueOb(Object obj, String msg) {
        return new JsonObject(Json.encode(new OneOb<>().setOb(obj).setMsg(msg)));
    }
}
