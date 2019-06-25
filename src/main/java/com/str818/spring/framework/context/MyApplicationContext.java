package com.str818.spring.framework.context;

import com.str818.spring.framework.beans.MyBeanFactory;
import com.str818.spring.framework.beans.config.MyBeanDefinition;
import com.str818.spring.framework.beans.support.MyBeanDefinitionReader;
import com.str818.spring.framework.beans.support.MyDefaultListableBeanFactory;

import java.util.List;

/**
 * @author ：str818
 * @description： IOC、DI、MVC、AOP
 * @date ：Created in 2019/6/25 16:04
 */
public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory {

    private String [] configLoactions;
    private MyBeanDefinitionReader reader;

    public MyApplicationContext(String... configLoactions){
        this.configLoactions = configLoactions;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void refresh() {

        // 1. 定位配置文件
        reader = new MyBeanDefinitionReader(this.configLoactions);

        // 2. 加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        
        // 3. 注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);
        
        // 4. 把不是延时加载的类，有提前初始化
        doAutowrited();
    }

    private void doAutowrited() {
    }

    private void doRegisterBeanDefinition(List<MyBeanDefinition> beanDefinitions) {
    }

    public Object getBean(String beanName) {
        return null;
    }
}
