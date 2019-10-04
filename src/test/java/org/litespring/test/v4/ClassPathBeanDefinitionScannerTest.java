package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.context.annotation.ScannedGenericBeanDefinition;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.stereotype.Component;

/**
 * @program: litespring->ClassPathBeanDefinitaionScannerTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-04 14:52
 **/
public class ClassPathBeanDefinitionScannerTest {

    /**
     * 测试ClassPathBeanDefinitionScanner对指定路径下的类读取转换为beanDefinition的功能
     */
    @Test
    public void testParseScanedBean(){

        // DefaultBeanFactory
        DefaultBeanFactory factory = new DefaultBeanFactory();

        // packages
        String basePackages = "org.litespring.service.v4,org.litespring.dao.v4";

        // scanner 持有 Factory 用来注册beanDefinition
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(factory);
        // 执行扫描
        scanner.doScan(basePackages);


        String annotation = Component.class.getName();

        {
            BeanDefinition bd = factory.getBeanDefinition("petStore");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();


            Assert.assertTrue(amd.hasAnnotation(annotation));
            AnnotationAttributes attributes = amd.getAnnotationAttributes(annotation);
            Assert.assertEquals("petStore", attributes.get("value"));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("accountDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
        {
            BeanDefinition bd = factory.getBeanDefinition("itemDao");
            Assert.assertTrue(bd instanceof ScannedGenericBeanDefinition);
            ScannedGenericBeanDefinition sbd = (ScannedGenericBeanDefinition)bd;
            AnnotationMetadata amd = sbd.getMetadata();
            Assert.assertTrue(amd.hasAnnotation(annotation));
        }
    }


}
