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


    /**
     * 将路径传进去 调用共同的构造 构造里会调用getResourceByPath(String path)这个方法,这个方法是谁构造函数就调用谁的实现方法
     * 这里注意:super()方法已经被继承 相当于在这里调用 就算父类不是抽象也同样会调用这里的实现
     * @param configFile
     */
    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }


    @Override
    protected Resource getResourceByPath(String path) {
        return new ClassPathResource(path,this.getBeanClassLoader());  ////ClassPathResource
    }
}
