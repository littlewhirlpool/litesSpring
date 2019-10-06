package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;
import org.litespring.dao.v4.AccountDao;
import org.litespring.service.v4.PetStoreService;

import java.lang.reflect.Field;

/**
 * @program: litespring->DependencyDescriptorTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-05 17:28
 **/
public class DependencyDescriptorTest {

    /**
     * 测试factory的resolveDependency功能 , 向其提供一个字段对象 返回一个bean
     * @throws NoSuchFieldException
     */
    @Test
    public void testResolveDependency() throws NoSuchFieldException {

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);
        ClassPathResource resource = new ClassPathResource("petstore-v4.xml");
        reader.loadBeanDefinitions(resource);

        Field f = PetStoreService.class.getDeclaredField("accountDao");
        DependencyDescriptor descriptor = new DependencyDescriptor(f,true);
        Object o = factory.resolveDependency(descriptor);
        Assert.assertTrue(o instanceof AccountDao);
    }
}
