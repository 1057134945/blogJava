package com.yanchao.blog.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ContextLoader;

/**
 * Bean容器工具类
 * 
 * @author: 王彦超[wang_yc@suixingpay.com]
 * @date: Apr 28, 2020 9:58:31 AM
 * @version: V1.0
 * @review: 王彦超[wang_yc@suixingpay.com]/Apr 28, 2020 9:58:31 AM
 */
@Slf4j
@Component
public final class ApplicationContextUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 通过Class获取Bean.
     *
     * @param <T>   范型
     * @param clazz 类类型
     * @return 类的Bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return getApplicationContext().getBean(clazz);
    }

    /**
     * 通过name,以及Class返回指定的Bean
     *
     * @param <T>   范型
     * @param name  Bean名称
     * @param clazz 类类型
     * @return 类的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        return getApplicationContext().getBean(name, clazz);
    }

    /**
     * 设置ApplicationContext
     *
     * @param context Spring boot 运行参数
     */
    public static void setApplicationContextByStatic(ApplicationContext context) {
        initContext(context);
    }

    /**
     * ApplicationContext初始化
     *
     * @param context Spring boot 运行参数
     */
    private static synchronized void initContext(ApplicationContext context) {
        log.info("[初始化 contextUtils]");
        if (applicationContext == null)
            applicationContext = context;

        if (applicationContext == null)
            applicationContext = ContextLoader.getCurrentWebApplicationContext();

        if (applicationContext == null) {
            log.info("[初始化 contextUtils 失败]");
            throw new ApplicationContextException("初始化失败");
        }
        log.info("[初始化 contextUtils 成功]");
    }

    /**
     * 获取ApplicationContext
     *
     * @return Spring boot 运行参数
     */
    private static ApplicationContext getApplicationContext() {
        if (applicationContext == null)
            initContext(null);
        return applicationContext;
    }

    /**
     * 设置ApplicationContext
     *
     * @param context Spring boot 运行参数
     * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
     */
    @Override
    public void setApplicationContext(ApplicationContext context) {
        initContext(context);
    }
}
