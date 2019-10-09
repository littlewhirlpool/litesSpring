package org.litespring.beans.factory.config;

import org.litespring.beans.BeansException;

/**
 * @program: litespring->BeanPostProcessor
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-07 16:29
 **/
public interface BeanPostProcessor {
    Object beforeInitialization(Object bean , String beanName) throws BeansException;

    Object afterInitialization(Object bean , String beanName) throws BeansException;

}
