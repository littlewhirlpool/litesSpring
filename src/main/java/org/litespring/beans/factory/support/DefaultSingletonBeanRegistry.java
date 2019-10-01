package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.SingletonBeanRegistry;
import org.litespring.util.Assert;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: litespring->DefaultSingletonBeanRegistry
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 13:15
 **/
public class DefaultSingletonBeanRegistry implements SingletonBeanRegistry {
    private final Map<String , Object> singletonObjects = new ConcurrentHashMap<String, Object>(64);

    public void registerSingleton(String beanName, Object singletonObject) {

        Assert.notNull(beanName , "'beanName' must not be null");

        Object oldObject = this.singletonObjects.get(beanName);
        if(oldObject != null) {
            throw new IllegalArgumentException("Could not register object [" +
                    singletonObject + "] under bean name '" + beanName + "' : there is already object ["+
                    oldObject + "] bound");
        }
        this.singletonObjects.put(beanName , singletonObject);
    }

    public Object getSingleton(String beanName) {
        return this.singletonObjects.get(beanName);
    }
}
