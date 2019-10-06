package org.litespring.beans.factory.annotation;

import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @program: litespring->AutowiredFieldElement
 * @description: 自动注入的字段类型
 * @author: weizhenfang
 * @create: 2019-10-06 10:59
 **/
public class AutowiredFieldElement extends InjectionElement {
    boolean required;

    public AutowiredFieldElement(Field f, boolean required, AutowireCapableBeanFactory factory) {
        super(f,factory);
        this.required = required;
    }

    public Field getField(){
        return (Field)this.member;
    }


    /**
     * 将this.fied 注入到targetClass
     * @param target
     */
    @Override
    public void inject(Object target) {

        Field field = this.getField();
        try {

            // 使用字段对象构造DependencyDescriptor 依赖描述对象
            DependencyDescriptor desc = new DependencyDescriptor(field, this.required);

            // 调用factory的resolveDependency功能 得到 对应的bean
            Object value = factory.resolveDependency(desc);

            if (value != null) {
                ReflectionUtils.makeAccessible(field);
                // 注入!! 注意:这里的field是PetStoreService的字节码对象得到的 , target是PetStoreService的实例
                field.set(target, value);
            }
        }
        catch (Throwable ex) {
            throw new BeanCreationException("Could not autowire field: " + field, ex);
        }
    }

}
