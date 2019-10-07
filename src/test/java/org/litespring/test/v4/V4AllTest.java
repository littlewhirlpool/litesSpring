package org.litespring.test.v4;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * @program: litespring->V1AllTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-29 22:51
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses(
        {
                ClassReaderTest.class,
                MetadataReaderTest.class,
                PackageResourceLoaderTest.class,
                ClassPathBeanDefinitionScannerTest.class,
                XmlBeanDefinitionReaderTest.class,
                InjectionMetadataTest.class,
                DependencyDescriptorTest.class,
                AutowiredAnnotationProcessorTest.class
        }
)
public class V4AllTest {
}
