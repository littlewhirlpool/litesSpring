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

    /**
     * 这里包含的开闭原则:这个方法是封闭的 修改关闭的
     * this.injectionElements 字段List<InjectionElement> 是开放的 可以自由扩展
     * 但是扩展的类要实现接口定义的inject方法 并且在这个方法内被调用
     * @param target
     */
    public void inject(Object target) {
        if (injectionElements == null || injectionElements.isEmpty()) {
            return;
        }
        // 遍历注解字段
        for (InjectionElement ele : injectionElements) {
            ele.inject(target);//注解里说的调用
        }
    }
}
