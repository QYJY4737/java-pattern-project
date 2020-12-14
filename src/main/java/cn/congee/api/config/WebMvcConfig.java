package cn.congee.api.config;

import cn.congee.api.interceptor.RequestLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 将拦截器注册到容器:WebMvcConfig
 *
 * @Author: yang
 * @Date: 2020-12-14 12:11
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public RequestLimitInterceptor getRequestLimitInterceptor(){
        return new RequestLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getRequestLimitInterceptor());
        super.addInterceptors(registry);
    }

}
