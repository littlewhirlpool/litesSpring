package org.litespring.beans.factory.support;

import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;

/**
 * @program: litespring->BeanDefinitionValueResolver
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 22:41
 **/
public class BeanDefinitionValueResolver {
    private final DefaultBeanFactory beanFactory;

    public BeanDefinitionValueResolver(DefaultBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 如果value是RuntimeBeanReference类型就使用BeanFactory返回bean对象
     * 如果是TypedStringValue类型就返回其TypedStringValue对象value值
     * @param value
     * @return
     */
    public Object resolveValueIfNecessary(Object value){
        if(value instanceof RuntimeBeanReference){
            RuntimeBeanReference ref = (RuntimeBeanReference) value;
            String refName = ref.getBeanName();
            Object bean = this.beanFactory.getBean(refName);
            return bean;
        }else if(value instanceof TypedStringValue){
            return ((TypedStringValue)value).getValue();
        }else{
            //TODO
            throw new RuntimeException("the value" + value + "has not implemented");
        }
    }
}
