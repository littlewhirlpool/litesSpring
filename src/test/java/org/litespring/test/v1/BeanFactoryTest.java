package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.service.v1.PetStoreService;

import static org.junit.Assert.*;

/**
 * @program: litespring->BeanFactoryTest
 * @description: BeanFactory测试用例
 * @author: weizhenfang
 * @create: 2019-09-24 20:06
 **/
public class BeanFactoryTest {

    @Test
    public void testGetBean() {

        // 初始化工厂
        BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");
        // 工厂类根据bean的名称得到bean的定义类
        BeanDefinition bd = factory.getBeanDefinition("petStore");

        assertEquals("org.litespring.service.v1.PetStoreService",bd.getBeanClassName());

        // 工厂根据beanid 得到bean
        PetStoreService petStore = (PetStoreService)factory.getBean("petStore");

        assertNotNull(petStore);
    }


    @Test
    public void testInvalidBean(){
         BeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");;

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
            new DefaultBeanFactory("xxx.xml");
        }catch (BeanDefinitionStoreException e){
            return;
        }
        Assert.fail("except BeanDefinitionStoreException");
    }
}
