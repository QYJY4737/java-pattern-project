package cn.congee.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;

/**
 * @Author: yang
 * @Date: 2020-12-10 12:30
 */
@Slf4j
@ComponentScan(basePackages = "cn.congee.*")
@MapperScan(value = "cn.congee.mapper")
@SpringBootApplication
@EnableCaching //开启缓存
@EnableScheduling //开启定时任务
@EnableSwagger2
public class JavaPatternProjectApplication extends SpringBootServletInitializer {

    @Value("${server.port}")
    private String SERVER_PORT;

    private static String APPLICATION_YML_SERVER_PORT;

    @PostConstruct
    private void init(){
        APPLICATION_YML_SERVER_PORT = SERVER_PORT;
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(JavaPatternProjectApplication.class);
    }

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        SpringApplication.run(JavaPatternProjectApplication.class,args);
        long end = System.currentTimeMillis();
        log.info("服务启动耗时为：" + (end - start) + "ms,服务端口为: " + APPLICATION_YML_SERVER_PORT);
    }

    /**
     * Springboot注入RestTemplate异常Field restTemplate in xxx.ApiRestUtils required a bea
     * @param factory
     * @return
     */
    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(180000);//单位为ms
        factory.setConnectTimeout(5000);//单位为ms
        return factory;
    }

}
