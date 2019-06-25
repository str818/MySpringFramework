package com.str818.spring.framework.beans;

/**
 * @author ：str818
 * @description：
 * @date ：Created in 2019/6/25 17:43
 */
public class MyBeanWrapper {

    private Object wrappedInstance;
    private Class<?> wrappedClass;

    public MyBeanWrapper(Object wrappedInstance) {
        this.wrappedInstance = wrappedInstance;
    }

    public Object getWrappedInstance(){
        return this.wrappedInstance;
    }

    public Class<?> getWrappedClass(){
        return this.wrappedInstance.getClass();
    }
}
