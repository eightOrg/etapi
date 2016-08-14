package com.eight.dao;

/**
 * Created by miaoch on 2016/8/9.
 */

import com.eight.pojo.Demo;
import com.eight.trundle.db.dao.BaseDao;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface DemoDao extends BaseDao<Demo>{
}
