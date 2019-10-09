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


    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path,this.getBeanClassLoader());  ////ClassPathResource
    }
}
