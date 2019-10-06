package org.litespring.beans.factory.config;

import org.litespring.beans.factory.BeanFactory;


/**
 * 可以自动注解
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
    // 处理依赖.
    public Object resolveDependency(DependencyDescriptor descriptor);
}
