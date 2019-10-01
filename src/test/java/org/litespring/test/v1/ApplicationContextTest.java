package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.context.ApplicationContext;
import org.litespring.context.support.ClassPathXmlApplicationContext;
import org.litespring.context.support.FileSystemApplicationContext;
import org.litespring.service.v1.PetStoreService;

import static javafx.scene.input.KeyCode.F;

/**
 * @program: litespring->ApplicationContextTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-29 22:31
 **/
public class ApplicationContextTest {


    @Test
    public void testGetBean(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService)ctx.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext(){
        // 注意啊 , 这里仍然hardcode了一个本地路径,这不是好的实践
//        ApplicationContext ctx = new FileSystemApplicationContext("G:\\code\\litespring\\src\\test\\resources\\petstore-v1.xml");
        ApplicationContext ctx = new FileSystemApplicationContext("src/test/resources/petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) ctx.getBean("petStore");
        Assert.assertNotNull(petStore);

    }
}
