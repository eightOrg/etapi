package com.demo.dao;

/**
 * Created by miaoch on 2016/8/9.
 */

import com.demo.untils.dao.BaseMapper;
import com.github.miemiedev.mybatis.paginator.domain.PageBounds;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Component
public interface UserMapper extends BaseMapper{

    Map getUserById(Integer id);
    List<Map> getUserList(Map params, PageBounds pb);
}
