package cn.starteasy.uaa.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * dubbo 在 springboot下的配置  指定使用xml配置方式
 * <p/>
 * 创建时间: 16/7/26 下午4:50<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Configuration
@ImportResource("classpath:spring.xml")
public class DubboConfig {
}
