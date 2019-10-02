package org.litespring.test.v2;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.TypeConverter;
import org.litespring.beans.TypeMismatchException;

import static org.junit.Assert.fail;

/**
 * @program: litespring->TypeConverterTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-02 09:57
 **/
public class TypeConverterTest {
    @Test
    public void testConvertStringToInt(){
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3" , Integer.class);
        Assert.assertEquals(3 , i.intValue());

        try {
            converter.convertIfNecessary("3.1" , Integer.class);
        }catch (TypeMismatchException e){
            return;
        }

        fail();
    }

    @Test
    public void testConvertStringToBoolean(){
        TypeConverter converter = new SimpleTypeConverter();
        Boolean b = converter.convertIfNecessary("true" , Boolean.class);
        Assert.assertEquals(true , b.booleanValue());

        try {
            converter.convertIfNecessary("sdfsd" , Integer.class);
        }catch (TypeMismatchException e){
            return;
        }

        fail();
    }
}
