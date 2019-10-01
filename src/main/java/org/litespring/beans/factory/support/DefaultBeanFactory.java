package org.litespring.beans.factory.support;

import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;
import org.litespring.util.ClassUtils;

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

        if(bd.isSingleton()){
            // 单例
            // 通过beanID获取实例
            Object bean = this.getSingleton(beanID);
            if(bean == null){
                // 获取为空 可能是未注册
                // 创建bean并注册
                bean = createBean(bd);
                this.registerSingleton(beanID , bean);
            }
            return bean;
        }

        return createBean(bd);
    }

    private Object createBean (BeanDefinition bd){
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
     * 注册类的定义
     * 属于BeanDefinitionRegistry接口供其他拥有factory对象的对象给factory注册类的定义
     * @param beanID
     * @param bd
     */
    public void registerBeanDefinition(String beanID, GenericBeanDefinition bd) {
        this.beanDefinitionMap.put(beanID,bd);
    }

    public void setBeanClassLoader(ClassLoader beanClassLoader) {
       this.beanClassLoader = beanClassLoader;
    }

    public ClassLoader getBeanClassLoader() {
        return (this.beanClassLoader != null ? this.beanClassLoader:ClassUtils.getDefaultClassLoader());
    }
}
