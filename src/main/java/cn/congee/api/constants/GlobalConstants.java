package cn.congee.api.constants;

import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;

/**
 * 全局常量定义
 *
 * @Author: yang
 * @Date: 2020-12-11 3:59
 */
public final class GlobalConstants {

    @Value("${server.port}")
    public String SERVER_PORT;

    public static String APPLICATION_YML_SERVER_PORT;

    @PostConstruct
    public void init(){
        APPLICATION_YML_SERVER_PORT = SERVER_PORT;
    }
}
