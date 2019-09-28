package org.litespring.beans;

/**
 * @program: litespring->BeansException
 * @description: beans的基本exception
 * @author: weizhenfang
 * @create: 2019-09-26 08:36
 **/
public class BeansException extends RuntimeException{
    public BeansException(String msg){
        super(msg);
    }

    public BeansException(String msg, Throwable cause){
        super(msg,cause);
    }
}
