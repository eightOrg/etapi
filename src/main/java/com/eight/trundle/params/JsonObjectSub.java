package com.eight.trundle.params;

import io.vertx.core.json.JsonObject;

import java.util.Objects;

public class JsonObjectSub extends JsonObject {
    @Override
    public String getString(String key) {
        Objects.requireNonNull(key);
        CharSequence cs = (CharSequence)this.getMap().get(key);
        return cs == null?null:cs.toString();
    }
    @Override
    public Integer getInteger(String key) {
        Objects.requireNonNull(key);
        Integer number=0;
        if(this.getMap().get(key) instanceof String)
            number =Integer.valueOf(this.getMap().get(key).toString());
        else
            number =(Integer)this.getMap().get(key);
        return number;
    }
    @Override
    public Long getLong(String key) {
        Objects.requireNonNull(key);
        Long number =0l;
        if(this.getMap().get(key) instanceof String)
            number =Long.valueOf(this.getMap().get(key).toString());
        else
            number =(Long)this.getMap().get(key);
        return number;
    }
    @Override
    public Double getDouble(String key) {
        Objects.requireNonNull(key);
        Double number =0d;
        if(this.getMap().get(key) instanceof String)
            number =Double.valueOf(this.getMap().get(key).toString());
        else
            number =(Double)this.getMap().get(key);
        return number;
    }
    @Override
    public Float getFloat(String key) {
        Objects.requireNonNull(key);
        Float number =0f;
        if(this.getMap().get(key) instanceof String)
            number =Float.valueOf(this.getMap().get(key).toString());
        else
            number =(Float)this.getMap().get(key);
        return number;
    }
    @Override
    public Boolean getBoolean(String key) {
        Objects.requireNonNull(key);
        Boolean number =false;
        if(this.getMap().get(key) instanceof String)
            number =Boolean.valueOf(this.getMap().get(key).toString());
        else
            number =(Boolean)this.getMap().get(key);
        return number;
    }
}
