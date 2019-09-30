package org.litespring.context.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;

/**
 * @program: litespring->ClassPathXmlApplicationContext
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-29 22:39
 **/
public class ClassPathXmlApplicationContext implements ApplicationContext {

    private DefaultBeanFactory factory = null;

    public ClassPathXmlApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        ClassPathResource resource = new ClassPathResource(configFile);
        reader.loadBeanDefinitions(resource);


    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
