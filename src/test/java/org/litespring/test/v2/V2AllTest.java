package org.litespring.test.v2;

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
                ApplicationContextTestV2.class,
                BeanDefinitionTestV2.class,
                BeanDefinitionValueResolverTest.class,
                CustomBooleanEditorTest.class,
                CustomNumberEditorTest.class,
                TypeConverterTest.class}
)
public class V2AllTest {
}
