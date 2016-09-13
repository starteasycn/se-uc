package cn.starteasy.uaa.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.dangdang.ddframe.rdb.sharding.api.MasterSlaveDataSourceFactory;
import com.dangdang.ddframe.rdb.sharding.config.common.api.config.ShardingRuleConfig;
import com.dangdang.ddframe.rdb.sharding.config.common.api.config.StrategyConfig;
import com.dangdang.ddframe.rdb.sharding.config.common.api.config.TableRuleConfig;
import com.dangdang.ddframe.rdb.sharding.spring.datasource.SpringShardingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 16/8/2 下午5:04<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Configuration
@EnableAutoConfiguration
public class ShardingDataSourceConfig {
//    @Autowired
//    private DataSource dataSource;

    @Value("${spring.datasource.driver-class-name}")
    String driverClassName;

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.username}")
    String username;

    @Value("${spring.datasource.password}")
    String password;

    @Value("${spring.datasource.slave.driver-class-name}")
    String slaveDriverClassName;

    @Value("${spring.datasource.slave.url}")
    String slaveUrl;

    @Value("${spring.datasource.slave.username}")
    String slaveUsername;

    @Value("${spring.datasource.slave.password}")
    String slavePassword;


    @Bean(name = "msDatasource")
    public DataSource initShardingDataSource(){
        DruidDataSource masterDataSource = new DruidDataSource();
        masterDataSource.setDriverClassName(driverClassName);
        masterDataSource.setUrl(url);
        masterDataSource.setUsername(username);
        masterDataSource.setPassword(password);

        DruidDataSource slaveDataSource = new DruidDataSource();
        slaveDataSource.setDriverClassName(slaveDriverClassName);
        slaveDataSource.setUrl(slaveUrl);
        slaveDataSource.setUsername(slaveUsername);
        slaveDataSource.setPassword(slavePassword);

        Map<String, DataSource> dataSourceMap = new HashMap<>();
        dataSourceMap.put("msDataSource", MasterSlaveDataSourceFactory.createDataSource("msds", masterDataSource, slaveDataSource));

        StrategyConfig databaseStrategy = new StrategyConfig();
        databaseStrategy.setAlgorithmExpression("msds");
        databaseStrategy.setShardingColumns("user_id");

        TableRuleConfig tableRuleConfig = new TableRuleConfig();
        tableRuleConfig.setDatabaseStrategy(databaseStrategy);
        tableRuleConfig.setActualTables("mock");

        Map<String, TableRuleConfig> tables = new HashMap<>();
        tables.put("mock", tableRuleConfig);

        ShardingRuleConfig shardingRuleConfig = new ShardingRuleConfig();
        shardingRuleConfig.setDataSource(dataSourceMap);
        shardingRuleConfig.setTables(tables);

        SpringShardingDataSource springShardingDataSource = new SpringShardingDataSource(shardingRuleConfig, new Properties());


        return springShardingDataSource;
//
//        DataSourceRule dataSourceRule = new DataSourceRule(createDataSourceMap(slaveDataSource), "");
//        TableRule mockTableRule = TableRule.builder("mock").actualTables(Arrays.asList("mock")).dataSourceRule(dataSourceRule).build();
//        DatabaseShardingStrategy databaseShardingStrategy = new DatabaseShardingStrategy("mock", new NoneDatabaseShardingAlgorithm());
////        TableRule orderItemTableRule = TableRule.builder("t_order_item").actualTables(Arrays.asList("t_order_item_0", "t_order_item_1")).dataSourceRule(dataSourceRule).build();
//        ShardingRule shardingRule = ShardingRule.builder().dataSourceRule(dataSourceRule)
//                .tableRules(Arrays.asList(mockTableRule))
//                .databaseShardingStrategy(databaseShardingStrategy)
//                //.bindingTableRules(Collections.singletonList(new BindingTableRule(Arrays.asList(mockTableRule))))
//                .build();
//        //.tableRules(Arrays.asList(orderTableRule, orderItemTableRule))
//               // .bindingTableRules(Collections.singletonList(new BindingTableRule(Arrays.asList(orderTableRule, orderItemTableRule)))).build();
//                //.databaseShardingStrategy(new DatabaseShardingStrategy("user_id", new ModuloDatabaseShardingAlgorithm()))
//                //.tableShardingStrategy(new TableShardingStrategy("order_id", new ModuloTableShardingAlgorithm())).build();
//        return new ShardingDataSource(shardingRule);

        //return MasterSlaveDataSourceFactory.createDataSource("logic_ds", slaveDataSource, slaveDataSource);
    }

    private static Map<String, DataSource> createDataSourceMap(DataSource slaveDataSource) {
        Map<String, DataSource> result = new HashMap<>(2);
        result.put("ds_0", slaveDataSource);
        result.put("ds_1", slaveDataSource);
        return result;
    }
}
