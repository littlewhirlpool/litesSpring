package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.*;

/**
 * @program: litespring->BeanFactoryTest
 * @description: BeanFactory测试用例
 * @author: weizhenfang
 * @create: 2019-09-24 20:06
 **/
public class BeanFactoryTest {
    DefaultBeanFactory factory = null;
    XmlBeanDefinitionReader reader = null;

    /**
     * 每次的测试用例使用的工厂对象和reader都是最新的 , 防止互相污染
     */
    @Before
    public void setUP(){
        factory = new DefaultBeanFactory();
        reader = new XmlBeanDefinitionReader(factory);  //组合优于继承,这里DefaultBeanFactory实现了两个接口 , reader需要其中一个接口的子类作为参数,那么factory满足条件使用
    }


    @Test
    public void testGetBean() {


        reader.loadBeanDefinitions("petstore-v1.xml");

        // 工厂类根据bean的名称得到bean的定义类
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());

        // 工厂根据beanid 得到bean
        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");

        assertNotNull(petStore);
    }


    @Test
    public void testInvalidBean(){
        reader.loadBeanDefinitions("petstore-v1.xml");

        try {
            factory.getBean("invalidBean");
        }catch (BeanCreationException e){
            return;
        }

        Assert.fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXML(){
        try {
            reader.loadBeanDefinitions("xxx.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("except BeanDefinitionStoreException");
    }
}
