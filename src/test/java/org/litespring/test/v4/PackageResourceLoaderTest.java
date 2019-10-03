package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;

import java.io.IOException;

/**
 * @program: litespring->PackageResourceLoaderTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-03 16:14
 **/
public class PackageResourceLoaderTest {

    @Test
    public void testGetResources() throws IOException{
        PackageResourceLoader loader = new PackageResourceLoader();
        Resource[] resources = loader.getResources("org.litespring.dao.v4");
        Assert.assertEquals(2 , resources.length);
    }
}
