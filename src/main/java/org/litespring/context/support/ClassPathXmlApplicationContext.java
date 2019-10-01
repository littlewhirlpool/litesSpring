package org.litespring.context.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

/**
 * @program: litespring->ClassPathXmlApplicationContext
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-29 22:39
 **/
public class ClassPathXmlApplicationContext extends AbstractApplicationContext implements ApplicationContext {


    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path);
    }
}
