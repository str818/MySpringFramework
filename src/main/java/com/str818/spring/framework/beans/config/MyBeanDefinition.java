package com.str818.spring.framework.beans.config;

import lombok.Data;

/**
 * @author ：str818
 * @description：
 * @date ：Created in 2019/6/25 16:13
 */
@Data
public class MyBeanDefinition {
    private String beanClassName;
    private boolean lazyInit = false;
    private String factoryBeanName;
}
