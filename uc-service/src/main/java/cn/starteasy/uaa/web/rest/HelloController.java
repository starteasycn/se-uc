/*
 * Copyright (c) 2013-2015, thinkjoy Inc. All Rights Reserved.
 *
 * Project Name: samples
 * $$Id: HelloController.java 15-5-24 下午10:28 $$
 */

package cn.starteasy.uaa.web.rest;


import cn.starteasy.uaa.config.MybatisProperties;
import cn.starteasy.uaa.domain.Tag;
import cn.starteasy.uaa.mapper.TagMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * TODO 一句话描述该类用途
 * <p/>
 * 创建时间: 15/5/24 下午10:28<br/>
 *
 * @author qyang
 * @since v0.0.1
 */
@RestController
public class HelloController {
    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private MybatisProperties properties;

    @RequestMapping("/")
    public String index() {
        logger.info("req index");
        return "Greetings from Spring Boot!";
    }

    @RequestMapping("/user/3")
    @ResponseBody
    public Map rest3() {
        logger.info("req rest3");
        logger.info(String.valueOf(tagMapper.findAll()));
        Map resData = new HashMap();
        resData.put("name", "yq11111111111");
        resData.put("sex", "male11111111");
        return resData;
    }


    @RequestMapping("/user/add/{mark}")
    @ResponseBody
    @Transactional(propagation = Propagation.REQUIRED)
    public Map add(@PathVariable("mark") String mark) {
        logger.info("req rest3");
        Tag tag = new Tag();
        tag.setName(String.valueOf(new Random().nextFloat()));
        if("1".equals(mark)) {
            logger.info(String.valueOf(tagMapper.insert(tag)));
        } else {

            logger.info(String.valueOf(tagMapper.insert(tag)));

            String nullStr = null;
            nullStr.toString();
            System.out.println("事务回滚");
        }
        Map resData = new HashMap();
        resData.put("name", tag.getName());
        resData.put("sex", "male11111111");
        return resData;
    }
}
