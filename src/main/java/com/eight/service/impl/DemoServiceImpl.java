package com.eight.service.impl;

import com.eight.pojo.Demo;
import com.eight.trundle.db.pojo.Identifiable;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.ob.ListOb;
import com.eight.dao.DemoMapper;
import com.eight.service.DemoService;
import com.eight.trundle.ob.OneOb;
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

/**
 * Created by miaoch on 2016/8/9.
 */
@Service("DemoService")
public class DemoServiceImpl implements DemoService {
    Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Autowired
    private DemoMapper demoMapper;

    @Override
    @Transactional(readOnly = true)
    public JsonObject demoMethod(JsonObject params) {
        ListOb<Demo> ob = new ListOb();
        Demo demo = new Demo();
        //------params - parse - demo
        PageBounds pb = new PageBounds(1,10);
        PageList<Demo> result=(PageList<Demo>)demoMapper.demoMethod(demo, pb);
        ob.setListob(result);
        ob.setPage(result.getPaginator());
        return new JsonObject(Json.encode(ob));
    }

    @Override
    public JsonObject blockingMethod(JsonObject params) {
        BaseOb ob;
        if (params.containsKey("token") && params.getString("token").equals("1")) {
            ob = new BaseOb();
        } else {
            ob = BaseOb.getFaildOb();
            ob.setMsg("默认拦截token不为1的请求，不允许使用接口");
        }
        return new JsonObject(Json.encode(ob));
    }

    @Override
    public JsonObject login(JsonObject params) {
       return params.containsKey("username") && params.getString("username").equals("hange")
               ?new JsonObject(Json.encode(new BaseOb()))
               :new JsonObject(Json.encode(BaseOb.getFaildOb()));
    }
}