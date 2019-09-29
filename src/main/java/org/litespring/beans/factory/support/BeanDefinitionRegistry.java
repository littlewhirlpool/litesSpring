package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;

public interface BeanDefinitionRegistry {
    /**
     * 获取类的定义
     * @param beanID
     * @return
     */
    BeanDefinition getBeanDefinition(String beanID  );

    /**
     * 注册类的定义
     * @param beanID
     * @param bd
     */
    void registerBeanDefinition(String beanID, GenericBeanDefinition bd);
}
