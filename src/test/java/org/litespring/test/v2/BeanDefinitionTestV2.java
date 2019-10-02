package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.beans.factory.xml.XmlBeanDefinitionReader;
import org.litespring.core.io.ClassPathResource;

import java.util.List;

/**
 * @program: litespring->BeanDefinitionTestV2
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 18:32
 **/
public class BeanDefinitionTestV2 {

    @Test
    public void testGetBeanDefiniton(){

        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory);

        reader.loadBeanDefinitions(new ClassPathResource("petstore-v2.xml"));

        BeanDefinition bd = factory.getBeanDefinition("petStore");

        List<PropertyValue> pvs = bd.getPropertyValues();

        // 校验是否是两个propertyValue
        Assert.assertTrue(pvs.size() == 4);
        // 校验accountDao为name属性的propertyvalue是否存在
        {
            PropertyValue pv = this.getPropertyValue("accountDao",pvs);

            Assert.assertNotNull(pv);

            Assert.assertTrue(pv.getValue() instanceof RuntimeBeanReference);
        }

    }


    private PropertyValue getPropertyValue(String name , List<PropertyValue> pvs){
        for(PropertyValue pv : pvs){
            if(pv.getName().equals(name)){
                return pv;
            }
        }
        return null;
    }
}
