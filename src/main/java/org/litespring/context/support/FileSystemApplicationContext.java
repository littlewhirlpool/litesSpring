package org.litespring.context.support;

import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

/**
 * @program: litespring->FileSystemApplicationContext
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-30 09:03
 **/
public class FileSystemApplicationContext extends AbstractApplicationContext {

    public FileSystemApplicationContext(String configFile) {
        super(configFile);

    }

    public Object getBean(String beanID) {
        return factory.getBean(beanID);
    }

    protected Resource getResourceByPath(String path) {
        return new FileSystemResource(path);  //使用FileSystemResource得到Resource
    }
}
