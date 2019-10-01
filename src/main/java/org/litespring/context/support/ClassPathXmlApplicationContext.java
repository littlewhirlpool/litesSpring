package org.litespring.context.support;

import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.Resource;

/**
 * @program: litespring->ClassPathXmlApplicationContext
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-29 22:39
 **/
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {


    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path);  ////ClassPathResource
    }
}
