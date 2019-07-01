package com.str818.spring.framework.context;

import com.str818.spring.framework.beans.MyBeanFactory;
import com.str818.spring.framework.beans.MyBeanWrapper;
import com.str818.spring.framework.beans.config.MyBeanDefinition;
import com.str818.spring.framework.beans.support.MyBeanDefinitionReader;
import com.str818.spring.framework.beans.support.MyDefaultListableBeanFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author ：str818
 * @description： IOC、DI、MVC、AOP
 * @date ：Created in 2019/6/25 16:04
 */
public class MyApplicationContext extends MyDefaultListableBeanFactory implements MyBeanFactory {

    private String [] configLoactions;

    // Bean 信息的读取类
    private MyBeanDefinitionReader reader;

    //单例的 IOC 容器缓存
    private Map<String,Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    // 通用的 IOC 容器
    private Map<String,MyBeanWrapper> factoryBeanInstanceCache = new ConcurrentHashMap<>();

    // 构造方法
    public MyApplicationContext(String... configLoactions){
        this.configLoactions = configLoactions;
        try {
            refresh();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void refresh() throws Exception {

        // 1. 定位配置文件
        reader = new MyBeanDefinitionReader(this.configLoactions);

        // 2. 加载配置文件，扫描相关的类，把它们封装成BeanDefinition
        List<MyBeanDefinition> beanDefinitions = reader.loadBeanDefinitions();
        
        // 3. 注册，把配置信息放到容器里面(伪IOC容器)
        doRegisterBeanDefinition(beanDefinitions);
        
        // 4. 把不是延时加载的类，有提前初始化
        doAutowrited();
    }

    // 只处理非延时加载的情况
    private void doAutowrited() {
        for (Map.Entry<String, MyBeanDefinition> beanDefinitionEntry : super.beanDefinitionMap.entrySet()) {
            String beanName = beanDefinitionEntry.getKey();
            if(!beanDefinitionEntry.getValue().isLazyInit()) {
                try {
                    getBean(beanName);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // 将配置信息放入到IOC容器中
    private void doRegisterBeanDefinition(List<MyBeanDefinition> beanDefinitions) throws Exception {

        for (MyBeanDefinition beanDefinition: beanDefinitions) {
            if(super.beanDefinitionMap.containsKey(beanDefinition.getFactoryBeanName())){
                throw new Exception("The “" + beanDefinition.getFactoryBeanName() + "” is exists!!");
            }
            super.beanDefinitionMap.put(beanDefinition.getFactoryBeanName(),beanDefinition);
        }
    }

    public Object getBean(String beanName) {
        return null;
    }
}
