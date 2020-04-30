package com.yanchao.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.yanchao.blog.util.ApplicationContextUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * 启动类
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 28, 2020 9:56:40 AM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 28, 2020 9:56:40 AM
 */
@Slf4j
@SpringBootApplication
public class BlogApplication {

    /**
     * 启动方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContextUtils.setApplicationContextByStatic(SpringApplication.run(BlogApplication.class, args));
        log.info("==============   Welcome To BlogApplication 彦超的博客  ==============");
    }
}
