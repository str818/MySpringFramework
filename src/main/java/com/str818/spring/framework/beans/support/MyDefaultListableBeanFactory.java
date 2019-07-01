package com.str818.spring.framework.beans.support;

import com.str818.spring.framework.beans.config.MyBeanDefinition;
import com.str818.spring.framework.context.support.MyAbstractApplicationContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：str818
 * @description： 伪 IOC 容器
 * @date ：Created in 2019/6/25 16:10
 */
public class MyDefaultListableBeanFactory extends MyAbstractApplicationContext {
    //存储注册信息的 BeanDefinition,伪 IOC 容器
    protected final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, MyBeanDefinition>();
}
