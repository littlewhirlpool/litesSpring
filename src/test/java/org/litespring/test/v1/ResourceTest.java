package org.litespring.test.v1;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.io.FileSystemResource;
import org.litespring.core.io.Resource;

import java.io.InputStream;


/**
 * @program: litespring->ResourceTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-29 23:01
 **/
public class ResourceTest {

    @Test
    public void testClassPathResource() throws Exception{
        Resource r = new ClassPathResource("petstore-v1.xml");

        InputStream is = null;

        try {
            is = r.getInputStream();
            // 注意:这个测试并不充分!!
            Assert.assertNotNull(is);
        }finally {
            if(is != null){
                is.close();
            }
        }
    }

    @Test
    public void testFileSystemResource() throws Exception{
//        Resource r = new FileSystemResource("G:\\code\\litespring\\src\\test\\resources\\petstore-v1.xml");
        Resource r = new FileSystemResource("src/test/resources/petstore-v1.xml");

        InputStream is = null;
        try {
            is = r.getInputStream();
            // 注意:这个测试并不充分!!
            Assert.assertNotNull(is);
        }finally {
            if(is != null){
                is.close();
            }
        }
    }
}
