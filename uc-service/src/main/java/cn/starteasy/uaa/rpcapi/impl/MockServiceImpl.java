package cn.starteasy.uaa.rpcapi.impl;

import cn.starteasy.uua.rpcapi.IServiceMock;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

/**
 * 基于 dubbox的java config声明rpc服务
 * <p>
 * 创建时间: 16/8/30 下午2:59<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@Service
@EnableAutoConfiguration
public class MockServiceImpl implements IServiceMock {
    @Override
    public void mock() {
        System.out.println("run here");
    }
}
