package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

/**
 * @program: litespring->GenericBeanDefinition
 * @description: BeanDefinition的实现类
 * @author: weizhenfang
 * @create: 2019-09-24 22:39
 **/
public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    public GenericBeanDefinition(String id,String beanClassName){
        this.id = id;
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName() {
        return this.beanClassName;
    }




}
