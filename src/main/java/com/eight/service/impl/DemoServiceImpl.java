package com.eight.service.impl;

import com.eight.dao.DemoDao;
import com.eight.pojo.Demo;
import com.eight.trundle.db.dao.BaseDao;
import com.eight.trundle.db.service.impl.BaseServiceImpl;
import com.eight.trundle.ob.BaseOb;
import com.eight.trundle.ob.ListOb;
import com.eight.service.DemoService;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import com.github.miemiedev.mybatis.paginator.domain.PageList;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Created by miaoch on 2016/8/9.
 */
@Service("DemoService")
public class DemoServiceImpl extends BaseServiceImpl<Demo> implements DemoService {
    Logger logger = LoggerFactory.getLogger(DemoServiceImpl.class);

    @Autowired
    private DemoDao demoDao;

    @Override
    protected BaseDao<Demo> getBaseDao() {
        return (BaseDao<Demo>) demoDao;
    }

}