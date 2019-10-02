package org.litespring.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.litespring.test.v1.V1AllTest;
import org.litespring.test.v2.V2AllTest;

/**
 * @program: litespring->AllTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-02 11:48
 **/
@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTest.class , V2AllTest.class})
public class AllTest {
}
