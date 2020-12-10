package cn.congee.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**
 * @Author: yang
 * @Date: 2020-12-11 3:20
 */
@Configuration
@EnableSwagger2
//第三部分--请求接口列表：在组范围内，只要被Swagger2扫描匹配到的请求都会在这里出现。
//第四部分--实体列表：只要实体在请求接口的返回值上（即使是泛型），都能映射到实体项中！
@ComponentScan("cn.congee.api.controller")
public class SwaggerConfig {

    @Bean
    public Docket docker(){
        // 构造函数传入初始化规范，这是swagger2规范
        return new Docket(DocumentationType.SWAGGER_2)
                //apiInfo： 添加api详情信息，参数为ApiInfo类型的参数，这个参数包含了第二部分的所有信息比如标题、描述、版本之类的，开发中一般都会自定义这些信息
                .apiInfo(apiInfo())

                //第一部分--API分组：如果没有配置分组默认是default。通过Swagger实例Docket的groupName()方法即可配置分组
                //.groupName("glonk")
                //配置是否启用Swagger，如果是false，在浏览器将无法访问，默认是true
                .enable(true)
                .select()
                //apis： 添加过滤条件,
                .apis(RequestHandlerSelectors.basePackage("cn.congee.api.controller"))
                //paths： 这里是控制哪些路径的api会被显示出来，比如下方的参数就是除了/user以外的其它路径都会生成api文档
                .paths(PathSelectors.any())
                .build()
                .pathMapping("/");
    }

    //第二部分--基本描述：可以通过Swagger实例Docket的apiInfo()方法中的ApiInfo实例参数配置文档信息
    private ApiInfo apiInfo(){
        Contact contact = new Contact("名字：QYJY4737", "个人链接：http://www.congee.com/", "邮箱：qyjy4737@163.com");
        return new ApiInfo(
                // 标题
                "本地服务",
                // 描述
                "本地服务接口文档",
                // 版本
                "版本内容：v1.0",
                // 组织链接
                "https://www.baidu.com/",
                // 联系人信息
                contact,
                // 许可
                "许可：Apach 2.0",
                // 许可连接
                "许可链接：XXX",
                // 扩展
                new ArrayList<>()
        );
    }

}
