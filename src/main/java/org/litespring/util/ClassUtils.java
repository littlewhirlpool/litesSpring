package org.litespring.util;

/**
 * @program: litespring->ClassUtils
 * @description: Class工具类
 * @author: weizhenfang
 * @create: 2019-09-24 22:42
 **/
public class ClassUtils {
    /**
     * 得到类的加载器
     * @return
     */
    public static ClassLoader getDefaultClassLoader(){
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        }
        catch (Throwable ex){

        }

        if(cl == null){
            cl = ClassUtils.class.getClassLoader();
            if(cl == null){
                try {
                    cl = ClassLoader.getSystemClassLoader();
                }
                catch (Throwable ex){

                }
            }
        }
        return cl;
    }
}
