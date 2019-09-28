package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: litespring->DefaultBeanFactory
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-24 21:07
 **/
public class DefaultBeanFactory implements BeanFactory , BeanDefinitionRegistry {
    // 类定义的id-定义 键值对  map  注意键值对的类型
    private final Map<String , BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();

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

        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();  //通过类的定义对象得到类的classname

        try {
            Class<?> clz = cl.loadClass(beanClassName);  //使用类的加载器 加载类的字节码对象
            return clz.newInstance(); //反射得到实例
        } catch (Exception e) {
            throw new BeanCreationException("create bean for" + beanClassName + " faild" , e);
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
}
