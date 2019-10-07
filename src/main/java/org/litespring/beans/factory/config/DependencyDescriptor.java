package org.litespring.beans.factory.config;

import org.litespring.util.Assert;

import java.lang.reflect.Field;

public class DependencyDescriptor {
    // java.lang.reflect.Field 反射的字段类型 包含name clazz 等字段
    private Field field;
    private boolean required;

    public DependencyDescriptor(Field field , boolean required){
        Assert.notNull(field , "Fiedl must not be null");
        this.field = field;
        this.required = required;
    }

    /**
     * 得到依赖的类型
     * 根据this.filed得到字节码其对象
     * @return
     */
    public Class<?> getDependencyType(){
        if(this.field != null){
            return field.getType();
        }
        // 还有构造参数注入 setter注入这里没做实现
        throw new RuntimeException("only support field dependency");
    }

    public boolean isRequired(){
        return this.required;
    }

}
