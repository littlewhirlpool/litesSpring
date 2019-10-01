package org.litespring.beans.factory.config;

/**
 * @program: litespring->TypedStringValue
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-01 19:30
 **/
public class TypedStringValue {
    private String value;
    public TypedStringValue(String value){
        this.value = value;
    }
    public String getValue(){
        return this.value;
    }
}
