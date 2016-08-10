package com.demo.service.impl;

import com.demo.dao.UserMapper;
import com.demo.service.UserService;
import com.demo.untils.ob.ListOb;
import com.demo.untils.ob.OneOb;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * Created by miaoch on 2016/8/9.
 */
@Service("userService")
public class UserServiceImpl implements UserService {
    Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public JsonObject getUserById(JsonObject params) {
        OneOb ob = new OneOb();
        int id = (int) params.getValue("id");
        //Map result=userMapper.getUserById(params.getMap());
        Map result=userMapper.getUserById(id);
        ob.setOb(result);
        return new JsonObject(Json.encode(ob));
    }

    @Override
    public JsonObject getUserList(JsonObject params) {
        ListOb ob = new ListOb();
        PageBounds pb=new PageBounds(1,10);
        PageList<Map> result=(PageList<Map>)userMapper.getUserList(params.getMap(),pb);
        ob.setListob(result);
        ob.setPage(result.getPaginator());
        return new JsonObject(Json.encode(ob));
    }

}