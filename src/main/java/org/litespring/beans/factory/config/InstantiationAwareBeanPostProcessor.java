package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * @program: litespring->InstantiationAwareBeanPostProcessor
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-07 16:33
 **/
public interface InstantiationAwareBeanPostProcessor extends BeanPostProcessor {

    Object beforeInstantiation(Class<?> beanClass , String beanName)throws BeansException;

    boolean afterInstantiation(Object bean , String beanName) throws BeansException;

    void postProcessPropertyValues(Object bean , String beanName) throws BeansException;
}
