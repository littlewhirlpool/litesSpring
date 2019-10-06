package org.litespring.beans;

import java.util.List;

/**
 * @program: litespring->BeanDefinition
 * @description: 类的定义接口
 * @author: weizhenfang
 * @create: 2019-09-24 21:53
 **/
public interface BeanDefinition {
    public static final String SCOPE_SINGLETON = "singleton";
    public static final String SCOPE_PROTOTYPE = "prototype";
    public static final String SCOPE_DEFAULT = "";
    public boolean isSingleton();
    public boolean isPrototye();
    String getScope();
    void setScope(String scope);
    String getBeanClassName() ;

    public List<PropertyValue> getPropertyValues();

    /**
     * 得到封装好的构造函数
     * @return
     */
    public ConstructorArgument getConstructorArgument();

    public String getID();

    public boolean hasConstructorArgumentValues();


    public Class<?> resolveBeanClass(ClassLoader classLoader) throws ClassNotFoundException;

    public Class<?> getBeanClass() throws IllegalArgumentException;

    public boolean hasBeanClass();


}
