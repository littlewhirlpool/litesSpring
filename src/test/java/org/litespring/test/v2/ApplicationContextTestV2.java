package org.litespring.test.v2;

import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.service.v2.PetStoreService;

import static junit.framework.TestCase.*;

/**
 * @program: litespring->test
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 18:38
 **/
public class ApplicationContextTestV2 {
    @Test
    public void testGetBeanProperty(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");

        // 测试注入是否成功
        assertNotNull(petStore.getAccountDao());
        assertNotNull(petStore.getItemDao());


        // 测试property 是 TypedString
        assertEquals("wzf" , petStore.getOwner());
        // 测试 TypedString 值为整型
        assertEquals(2 , petStore.getVersion());
    }

}
