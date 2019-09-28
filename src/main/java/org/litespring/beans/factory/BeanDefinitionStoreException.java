package org.litespring.beans.factory;

import org.litespring.beans.BeansException;

/**
 * @program: litespring->BeanDefinitionStoreException
 * @description: BeanDefinition相关异常
 * @author: weizhenfang
 * @create: 2019-09-26 20:15
 **/
public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String msg , Throwable cause){
        super(msg , cause);
    }
}
