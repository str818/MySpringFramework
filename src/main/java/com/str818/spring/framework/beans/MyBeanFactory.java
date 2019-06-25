package com.str818.spring.framework.beans;

/**
 * @author ：str818
 * @description：单例工厂的顶层设计
 * @date ：Created in 2019/6/25 16:00
 */
public interface MyBeanFactory {
    /**
     * 根据 beanName 从 IOC 容器中获得一个实例 Bean
     * @param beanName
     * @return
     */
    Object getBean(String beanName);
}
