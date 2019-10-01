package org.litespring.beans.factory.config;

/**
 * @program: litespring->RuntimeBeanReference
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 19:26
 **/
public class RuntimeBeanReference {
    private final String beanName;
    public RuntimeBeanReference(String beanName){
        this.beanName = beanName;
    }

    public String getBeanName(){
        return this.beanName;
    }

}
