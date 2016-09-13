package cn.starteasy.uaa.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 16/7/29 下午4:48<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Configuration
//TODO 注意，由于MapperScannerConfigurer执行的比较早，所以必须有下面的注解
//@AutoConfigureAfter(MyBatisConfig.class)
@EnableAutoConfiguration
@EnableConfigurationProperties(MybatisProperties.class)
public class MyBatisMapperScannerConfig {
    @Autowired
    private MybatisProperties properties;

//    @Value("${spring.mybatis.mapperPackage}")
//    String mapperPackage;
    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer(@Value("${spring.datasource.url}") String url) {

        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
        mapperScannerConfigurer.setBasePackage("cn.starteasy.uaa.mapper");
        return mapperScannerConfigurer;
    }

}
