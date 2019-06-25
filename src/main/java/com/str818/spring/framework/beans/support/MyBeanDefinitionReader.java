package com.str818.spring.framework.beans.support;

import com.str818.spring.framework.beans.config.MyBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * @author ：str818
 * @description：
 * @date ：Created in 2019/6/25 16:36
 */
public class MyBeanDefinitionReader {

    // 存储所有类的名称
    private List<String> registyBeanClasses = new ArrayList<>();

    // 配置文件属性信息
    private Properties config = new Properties();

    // 固定配置文件中的 Key，相对于 XML 的规范
    private final String SCAN_PACKAGE = "scanPackage";

    public MyBeanDefinitionReader(String...locations) {
        // 通过 URL 定位找到其所对应的文件，然后转换为文件流
        InputStream is = this.getClass().getClassLoader().getResourceAsStream(locations[0].replace("classpath:",""));
        try {
            config.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(null != is){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // 扫描包路径
        doScanner(config.getProperty(SCAN_PACKAGE));
    }

    // 扫描包路径下的所有 class 文件
    private void doScanner(String scanPackage) {
        // 转换为文件路径，实际上就是把.替换为/就OK了
        URL url = this.getClass().getResource("/" + scanPackage.replaceAll("\\.","/"));
        File classPath = new File(url.getFile());
        for (File file : classPath.listFiles()) {
            if(file.isDirectory()){
                doScanner(scanPackage + "." + file.getName());
            }else{
                if(!file.getName().endsWith(".class")){ continue;}
                String className = (scanPackage + "." + file.getName().replace(".class",""));
                registyBeanClasses.add(className);
            }
        }
    }

    // 获取配置信息
    public Properties getConfig(){
        return this.config;
    }

    // 把配置文件中扫描到的所有的配置信息转换为 MyBeanDefinition 对象，以便于之后IOC操作方便
    public List<MyBeanDefinition> loadBeanDefinitions(String...locations) {
        List<MyBeanDefinition> result = new ArrayList<>();
        try {
            for (String className : registyBeanClasses) {
                Class<?> beanClass = Class.forName(className);
                //如果是一个接口，是不能实例化的
                //用它实现类来实例化
                if(beanClass.isInterface()) { continue; }

                // beanName有三种情况:
                // 1. 默认是类名首字母小写
                // 2. 自定义名字
                // 3. 接口注入
                result.add(doCreateBeanDefinition(toLowerFirstCase(beanClass.getSimpleName()),beanClass.getName()));

                // 创建该类实现的接口
                Class<?> [] interfaces = beanClass.getInterfaces();
                for (Class<?> i : interfaces) {
                    //如果是多个实现类，只能覆盖
                    result.add(doCreateBeanDefinition(i.getName(),beanClass.getName()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    // 把每一个配信息解析成一个BeanDefinition
    private MyBeanDefinition doCreateBeanDefinition(String factoryBeanName, String beanClassName){
        MyBeanDefinition beanDefinition = new MyBeanDefinition();
        beanDefinition.setBeanClassName(beanClassName);
        beanDefinition.setFactoryBeanName(factoryBeanName);
        return beanDefinition;
    }

    // 类名首字母转为小写
    private String toLowerFirstCase(String simpleName) {
        char[] chars = simpleName.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
