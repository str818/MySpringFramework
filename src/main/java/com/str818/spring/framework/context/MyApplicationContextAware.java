package com.str818.spring.framework.context;

import com.str818.spring.framework.context.MyApplicationContext;

/**
 * @author ：str818
 * @description： 通过解耦方式获得IOC容器的顶层设计
 * 后面将通过一个监听器去扫描所有的类，只要实现了此接口，
 * 将自动调用 setApplicationContext()方法，从而将IOC容器注入到目标类中
 * @date ：Created in 2019/6/25 16:18
 */
public interface MyApplicationContextAware {
    void setApplicationContext(MyApplicationContext applicationContext);
}
