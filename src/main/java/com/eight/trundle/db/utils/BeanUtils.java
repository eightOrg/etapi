package com.eight.trundle.db.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class BeanUtils {
	
	public static Map convertBeanToMap(Object bean) {
		Class type = bean.getClass();
        Map returnMap = new HashMap();
        try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type);

			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
			    PropertyDescriptor descriptor = propertyDescriptors[i];
			    String propertyName = descriptor.getName();
			    if (!propertyName.equals("class")) {
			        Method readMethod = descriptor.getReadMethod();
			        Object result = readMethod.invoke(bean, new Object[0]);
			        if (result != null) {
			            returnMap.put(propertyName, result);
			        }
			    }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return returnMap; 
    }

}
