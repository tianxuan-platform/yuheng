package com.wuyiccc.yuheng.infrastructure.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2024/1/17 10:31
 * <br>
 * spring工具类
 */
@Component
public class SpringUtils implements BeanFactoryPostProcessor, ApplicationContextAware {

    private static ConfigurableListableBeanFactory beanFactory;

    private static ApplicationContext applicationContext;


    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        SpringUtils.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringUtils.applicationContext = applicationContext;
    }

    public static ListableBeanFactory getBeanFactory() {

        final ListableBeanFactory factory = Objects.isNull(beanFactory) ? applicationContext : beanFactory;
        if (Objects.isNull(factory)) {
            throw new RuntimeException("cannot get ConfigurableListableBeanFactory or ApplicationContext");
        }

        return factory;
    }

    public static <T> T getBean(Class<T> clazz) {

        return (T) getBeanFactory().getBean(clazz);
    }
}
