package org.litespring.test.v3;

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
                ApplicationContextTestv3.class,
                BeanDefinitionTestV3.class,
                ConstructorResolverTest.class}
)
public class V3AllTest {
}
