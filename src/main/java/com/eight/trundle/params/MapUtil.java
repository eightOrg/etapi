package com.eight.trundle.params;

import io.vertx.core.json.JsonObject;

import java.util.Map;

/**
 * Created by miaoch on 2016/8/14.
 */
public class MapUtil {

    /**
     * 检测key存在且非空
     * @param map
     * @param key
     * @return
     */
    public static boolean isNotEmpty (Map map, String key) {
        return map.containsKey(key) && !map.get(key).toString().equals("");
    }

    /**
     * 检测key存在且非空
     * @param obj
     * @param key
     * @return
     */
    public static boolean isNotEmpty (JsonObject obj, String key) {
        return isNotEmpty(obj.getMap(), key);
    }

    /**
     * 检测key不存在或为空
     * @param map
     * @param key
     * @return
     */
    public static boolean isEmpty (Map map, String key) {
        return !map.containsKey(key) || map.get(key).toString().equals("");
    }

    /**
     * 检测key不存在或为空
     * @param obj
     * @param key
     * @return
     */
    public static boolean isEmpty (JsonObject obj, String key) {
        return isEmpty(obj.getMap(), key);
    }
}
