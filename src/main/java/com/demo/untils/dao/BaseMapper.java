package com.demo.untils.dao;

/**
 * Created by miaoch on 2016/4/5.
 */


import com.github.miemiedev.mybatis.paginator.domain.PageBounds;

import java.util.List;

public interface BaseMapper<T> {
    T findOne(T map);
    void insert(T map);
    void update(T map);
    void delete(T map);
    List<T> findPage(T map, PageBounds pb);

}
