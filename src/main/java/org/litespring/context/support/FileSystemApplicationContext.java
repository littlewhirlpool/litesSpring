package org.litespring.context.support;

import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.context.ApplicationContext;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

/**
 * @program: litespring->FileSystemApplicationContext
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-30 09:03
 **/
public class FileSystemApplicationContext implements ApplicationContext {
    private DefaultBeanFactory factory = null;

    public FileSystemApplicationContext(String configFile) {
        factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        Resource resource = new FileSystemResource(configFile);
        reader.loadBeanDefinitions(resource);


    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }
}
