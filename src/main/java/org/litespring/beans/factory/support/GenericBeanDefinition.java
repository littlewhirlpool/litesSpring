package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: litespring->GenericBeanDefinition
 * @description: BeanDefinition的实现类
 * @author: weizhenfang
 * @create: 2019-09-24 22:39
 **/
public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String beanClassName;
    private boolean singleton = true;  //默认为true
    private boolean prototype = false;  // 默认为false
    private String scope = SCOPE_DEFAULT;

    // 属性
    List<PropertyValue> propertyValues = new ArrayList<PropertyValue>();
    // 构造
    ConstructorArgument constructorArgument = new ConstructorArgument();

    public GenericBeanDefinition(String id,String beanClassName){
        this.id = id;
        this.beanClassName = beanClassName;
    }
    public GenericBeanDefinition() {

    }

    public String getBeanClassName() {
        return this.beanClassName;
    }
    public void setBeanClassName(String className){
        this.beanClassName = className;
    }

    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }

    public ConstructorArgument getConstructorArgument() {
        return this.constructorArgument;
    }

    public String getID() {
        return this.id;
    }

    public boolean hasConstructorArgumentValues() {
        return !this.constructorArgument.isEmpty();
    }

    public boolean isSingleton() {
        return this.singleton;
    }

    public boolean isPrototye() {
        return this.prototype;
    }

    public String getScope() {
        return this.scope;
    }

    public void setId(String id){
        this.id = id;
    }
    /**
     * set scope 属性的时候 更新singleton和prototype的属性
     * @param scope
     */
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);

    }



}
