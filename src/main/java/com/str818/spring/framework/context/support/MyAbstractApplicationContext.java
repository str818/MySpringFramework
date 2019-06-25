package com.str818.spring.framework.context.support;

/**
 * @author ：str818
 * @description： IOC 容器实现的顶层设计
 * @date ：Created in 2019/6/25 16:06
 */
public abstract class MyAbstractApplicationContext {
    // 受保护，只提供给子类重写
    protected void refresh() throws Exception{}

}
