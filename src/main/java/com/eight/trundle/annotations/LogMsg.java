package com.eight.trundle.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 日志记录描述（用于记录系统日志时的描述字段内容）
 * 一般用以增删改查操作的方法，也可以用以GET请求的普通资源访问方法
 * @author micktiger
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented  
@Inherited
public @interface LogMsg {
	
	
	/**
	 * 方法名描述
	 * @return
	 */
	String value() default "";

}
