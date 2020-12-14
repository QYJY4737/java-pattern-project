package cn.congee.api.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * @Author: yang
 * @Date: 2020-12-14 12:03
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
@Order(Ordered.HIGHEST_PRECEDENCE)
public @interface RequestLimit {
    /**
     * 允许访问的最大次数
     * @return
     */
    int count() default 15;

    /**
     * 时间段，单位为毫秒，默认值一分钟
     * @return
     */
    long time() default 1000 * 60;
}
