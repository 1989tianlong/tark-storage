package com.tark.storage.security.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  * 在Controller的方法参数中使用此注解，该方法在映射时会注入当前登录的User对象
 * @see com.tark.storage.security.resolvers.CurrentUserMethodArgumentResolver
 * Created by jh on 2017/5/4.
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface CurrentUser {
}
