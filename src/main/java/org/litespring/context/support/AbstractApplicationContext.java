package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.Resource;
import org.litespring.util.ClassUtils;

/**
 * 模板模式:
 *提取出共同的方法 , 抽象出不同实现的接口方法
 * @program: litespring->AbstractApplicationContext
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-30 23:58
 **/
public abstract class AbstractApplicationContext implements ApplicationContext {
    protected DefaultBeanFactory factory = null;  //protected修饰 子类继承

    /**
     * 共同的构造代码
     * ApplicationContext的功能:封装对factory的使用
     * ApplicationContext
     * @param configFile
     */
    public AbstractApplicationContext(String configFile) {
        // 新建factory对象
        factory = new DefaultBeanFactory();
        // 将factory传递给BeanDefinitionReader 这样当它读取配置文件后就可以调用factory的registerBeanDefinition祖册类的定义
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = getResourceByPath(configFile);
        reader.loadBeanDefinitions(resource);
    }

    /**
     * 共同的
     * @param beanID
     * @return
     */
    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    /**
     * 抽象方法:相同的接口,不同的实现
     * @param path
     * @return
     */
    protected abstract Resource getResourceByPath (String path);
}
