package com.eight.dao;

/**
 * Created by miaoch on 2016/8/9.
 */

import com.eight.pojo.Demo;
import com.eight.trundle.db.dao.BaseMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemoMapper extends BaseMapper<Demo>{
    List<Demo> demoMethod(Demo demo, PageBounds pb);
}
