package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @program: litespring->InjectionElement
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-06 10:59
 **/
public abstract class InjectionElement {
    protected Member member; // Member : 字段的父类
    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member,AutowireCapableBeanFactory factory){
        this.member = member;
        this.factory = factory;
    }

    public abstract void inject(Object target);

}
