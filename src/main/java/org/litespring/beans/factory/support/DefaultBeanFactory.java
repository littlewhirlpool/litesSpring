package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: litespring->DefaultBeanFactory
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-24 21:07
 **/
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry
        implements  BeanDefinitionRegistry , ConfigurableBeanFactory {
    // 类定义的id-定义 键值对  map  注意键值对的类型
    private final Map<String , BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();
    private ClassLoader beanClassLoader;

    public DefaultBeanFactory() {
    }


    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    /**
     * 根据beanid 返回一个bean实例
     * @param beanID
     * @return
     */
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);  //通过id得到类的定义对象
        if (bd == null){
            throw new BeanCreationException("Bean Definition does not exist");
        }

        // 如果是单例 就从单例里获取 如果不是就新建一个对象
        if(bd.isSingleton()){
            // 单例
            // 通过beanID获取实例
            Object bean = this.getSingleton(beanID);
            if(bean == null){
                // 获取为空 可能是未注册
                // 创建bean
                bean = createBean(bd);
                // 并注册
                this.registerSingleton(beanID , bean);
            }
            return bean;
        }

        return createBean(bd);
    }

    private Object createBean (BeanDefinition bd){
        // 创建实例
        Object bean = instantiateBean(bd);
        // 设置属性
        populateBean(bd,bean);

        return bean;
    }

    /**
     * 使用类的加载器 根据beanDefinition中的全路径类名得到字节码对象 反射为对象
     * @param bd
     * @return
     */
    private Object instantiateBean(BeanDefinition bd){
        if(bd.hasConstructorArgumentValues()){
            ConstructorResolver resolver = new ConstructorResolver(this);
            return resolver.autowireConstructor(bd);
        }
        ClassLoader cl = this.getBeanClassLoader();
        //通过类的定义对象得到类的classname
        String beanClassName = bd.getBeanClassName();
        try {
            //使用类的加载器 加载类的字节码对象
            Class<?> clz = cl.loadClass(beanClassName);
            return clz.newInstance();
        }catch (Exception e){
            throw new BeanCreationException("create bean for " + beanClassName + "failed" , e);
        }
    }

    /**
     * 补充完善bean
     * @param bd
     * @param bean
     */
    protected void populateBean(BeanDefinition bd , Object bean){
        // 得到bd 的 PropertyValues
        List<PropertyValue> pvs = bd.getPropertyValues();

        if(pvs == null || pvs.isEmpty()){
            return;
        }

        // 得到 BeanDefinitionValueResolver 对象 用来得到BeanDefinitionValue(bd的propertyValues封装的是property标签对象)映射的对象
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this);

        try {
            // 得到bean的BeanInfo对象
            BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
            // 得到bean的property的description
            PropertyDescriptor[] pds = beanInfo.getPropertyDescriptors();
            // 遍历每一个property标签解析出的 PropertyValue对象
            for (PropertyValue pv : pvs) {
                String propertyName = pv.getName();
                Object originalValue = pv.getValue();
                // 调用resolveValueIfNecessary方法传入value 得到映射的对象(ref/typedString)
                Object resolvedValue = valueResolver.resolveValueIfNecessary(originalValue);
                SimpleTypeConverter converter = new SimpleTypeConverter();


                for (PropertyDescriptor pd : pds) {
                    // 如果bean的属性名和配置文件得到的pv的name相同
                    if(pd.getName().equals(propertyName)){
                        // 如果有必要就转化  pd.getPropertyType:bean属性的类型
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, pd.getPropertyType());
                        // 得到该属性的set方法 将对于的对象传递进去  setter注入成功!!
                        pd.getWriteMethod().invoke(bean,convertedValue);
                        break;
                    }
                }
            }
        }catch (Exception e){
            throw new BeanCreationException("Failed to obtain BeanInfo for class ["
            + bd.getBeanClassName() + "]",e);
        }
    }


    /**
     * 注册类的定义
     * 属于BeanDefinitionRegistry接口供其他拥有factory对象的对象给factory注册类的定义
     * @param beanID
     * @param bd
     */
    public void registerBeanDefinition(String beanID, BeanDefinition bd) {
        this.beanDefinitionMap.put(beanID,bd);
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
       this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader:ClassUtils.getDefaultClassLoader());
    }

    public Object resolveDependency(DependencyDescriptor descriptor) {
        Class<?> typeToMatch = descriptor.getDependencyTye();
        // 遍历所有的bean的定义
        for (BeanDefinition bd : this.beanDefinitionMap.values()) {
            // 调用resolveBeanClass确保BeanDefinition 有 Class对象
            resolveBeanClass(bd);
            // 得到bd的beanClass
            Class<?> beanClass = bd.getBeanClass();
            // 如果bd的beanClass能够
            if(typeToMatch.isAssignableFrom(beanClass)){
                return this.getBean(bd.getID());
            }
        }
        return null;
    }

    public void resolveBeanClass(BeanDefinition bd ){
        if(bd.hasBeanClass()){
            return;
        } else {
            try {
                bd.resolveBeanClass(this.getBeanClassLoader());
            }catch (ClassNotFoundException e){
                throw new RuntimeException("can't load class" + bd.getBeanClassName());
            }
        }
    }
}
