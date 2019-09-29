package org.litespring.test.v1;

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
        {ApplicationContextTest.class,
        BeanFactoryTest.class}
)
public class V1AllTest {
}
