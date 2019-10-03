package org.litespring.beans.factory.annotation;

import java.lang.annotation.*;

/**
 * @program: litespring->Autowired
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-03 18:15
 **/
@Target({ElementType.CONSTRUCTOR,
ElementType.FIELD,
ElementType.METHOD,
ElementType.ANNOTATION_TYPE
})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {


    /**
     * Declares whether the annotated dependency is required
     * <p>Defaults to {@code true}</p>
     * @return
     */
    boolean required() default true;
}
