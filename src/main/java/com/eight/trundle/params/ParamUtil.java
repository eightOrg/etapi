package com.eight.trundle.params;

import io.vertx.core.MultiMap;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.util.Iterator;
import java.util.Map;

public class ParamUtil {

    private static String ingorKey="name,search";

    public static JsonObject getParamJson(MultiMap paramMap){
        JsonObject param=new JsonObject();
        if (paramMap!=null){
            Iterator iter = paramMap.entries().iterator();
            while(iter.hasNext()) {
                Map.Entry<String, Object> entry = (Map.Entry<String, Object>) iter.next();
                System.out.println(entry.getKey() + "====" + entry.getValue());
                if(entry.getValue() instanceof String){
                    if (param.containsKey(entry.getKey())){//多值
                        if (!(param.getValue(entry.getKey()) instanceof JsonArray)) {
                            param.put(entry.getKey(), paramMap.getAll(entry.getKey()));
                            //直接转
                            continue;
                        }
                    }else{
                        try {
                            if(ingorKey.contains(entry.getKey())){
                                param.put(entry.getKey(),entry.getValue());
                            }else {
                                param.put(entry.getKey(), Integer.valueOf(entry.getValue().toString()));
                            }
                        }catch (Exception e) {
                            param.put(entry.getKey(),entry.getValue());
                        }
                    }
                }else{
                    param.put(entry.getKey(),entry.getValue());
                }
            }
        }
        return param;
    }
}