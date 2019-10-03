package org.litespring.stereotype;

import java.lang.annotation.*;

/**
 * @program: litespring->Component
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-03 18:20
 **/
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Component {


    /**
     * The value may indicate a suggestion for a logical component name,
     * to be turned into a Spring bean in case of an autodetected component.
     * @return the suggested component name, if any
     */
    String value() default "";
}
