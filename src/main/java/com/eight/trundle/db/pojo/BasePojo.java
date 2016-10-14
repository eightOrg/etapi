package com.eight.trundle.db.pojo;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.beans.BeanUtils.getPropertyDescriptor;

/**
 * Created by miaoch on 2016/10/14.
 */
public class BasePojo implements Identifiable {

    @Override
    public Map parseMap() {
        Map map = new HashMap();
        Class clazz = this.getClass();
        List<Field> fieldList = findAllFieldsOfSelfAndSuperClass(clazz);
        Field field = null;
        try {
            for (int i = 0; i < fieldList.size(); i++) {
                field = fieldList.get(i);
                Object value = getProperty(this, field.getName());
                if (null != value
                        && !"".equals(value.toString())) {
                    map.put(field.getName(),
                            getProperty(this, field.getName()));
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取一个类和其父类的所有属性
     *
     * @param clazz
     * @return
     */
    public static List<Field> findAllFieldsOfSelfAndSuperClass(Class clazz) {
        Field[] fields = null;
        List fieldList = new ArrayList();
        while (true) {
            if (clazz == null) {
                break;
            } else {
                fields = clazz.getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    fieldList.add(fields[i]);
                }
                clazz = clazz.getSuperclass();
            }
        }
        return fieldList;
    }

    public static Object getProperty(Object obj, String propertyName) {
        Class clazz = obj.getClass();// 获取对象的类型
        PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);// 获取 clazz
        // 类型中的
        // propertyName
        // 的属性描述器
        Method getMethod = pd.getReadMethod();// 从属性描述器中获取 get 方法
        Object value = null;
        try {
            value = getMethod.invoke(obj, new Object[] {});// 调用方法获取方法的返回值
        } catch (Exception e) {
            e.printStackTrace();
        }
        return value;// 返回值
    }
}
