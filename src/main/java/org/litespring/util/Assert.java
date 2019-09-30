package org.litespring.util;

/**
 * @program: litespring->Assert
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-30 08:33
 **/
public class Assert {
    public static void notNull(Object object , String message){
        if(object == null){
            throw new IllegalArgumentException(message);
        }
    }
}
