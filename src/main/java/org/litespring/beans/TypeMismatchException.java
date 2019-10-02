package org.litespring.beans;

/**
 * @program: litespring->TypeMismatchException
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-02 10:05
 **/
public class TypeMismatchException extends BeansException {
    private transient Object value;  //transient java关键字 修饰的变量不参与序列化

    private Class<?> requiredType;

    public TypeMismatchException(Object value , Class<?> requiredType){
        super("Failed to convert value : " + value + "to type " + requiredType);
        this.value = value;
        this.requiredType = requiredType;
    }

    public Object getValue(){
        return value;
    }

    public Class<?> getRequiredType(){
        return requiredType;
    }


}
