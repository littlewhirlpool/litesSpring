package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

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
     * 共同的方法
     * @param configFile
     */
    public AbstractApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
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
