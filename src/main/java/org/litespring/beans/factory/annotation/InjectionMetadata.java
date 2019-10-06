package org.litespring.beans.factory.annotation;

import java.util.List;

/**
 * @program: litespring->InjectionMetadata
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-06 11:04
 **/
public class InjectionMetadata {

    // 目标字节码对象
    private final Class<?> targetClass;
    // 要注入的字段
    private List<InjectionElement> injectionElements;

    public InjectionMetadata(Class<?> targetClass, List<InjectionElement> injectionElements) {
        this.targetClass = targetClass;
        this.injectionElements = injectionElements;
    }

    public List<InjectionElement> getInjectionElements() {
        return injectionElements;
    }

    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        // 遍历注解字段
        for (InjectionElement ele : injectionElements) {
            ele.inject(target);
        }
    }
}
