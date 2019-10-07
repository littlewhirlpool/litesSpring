package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.config.AutowireCapableBeanFactory;

import java.lang.reflect.Member;

/**
 * @program: litespring->InjectionElement
 * @description:抽象类
 * @author: weizhenfang
 * @create: 2019-10-06 10:59
 * 持有AutowireCapableBeanFactory factory member表示要注入的字段
 * inject方法将字段需要的bean注入到targetObj的对应字段上
 **/
public abstract class InjectionElement {
    protected Member member; // Member : 字段的父类
    protected AutowireCapableBeanFactory factory;

    InjectionElement(Member member,AutowireCapableBeanFactory factory){
        this.member = member;
        this.factory = factory;
    }

    /**
     *
     * @param target
     */
    public abstract void inject(Object target);

}
