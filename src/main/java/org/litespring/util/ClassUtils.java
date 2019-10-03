package org.litespring.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: litespring->ClassUtils
 * @description: Class工具类
 * @author: weizhenfang
 * @create: 2019-09-24 22:42
 **/
public class ClassUtils {

    /** the package separator character : '.' */
    private static final char PACKAGE_SEPARATOR = '.';

    /** the path separator character : '/' */
    private static final char PATH_SEPARATOR = '/';

    /** the inner class separator character: '$' */
    private static final char INNER_CLASS_SEPARATOR = '$';

    /** the CGLIB class separator character: '$$' */
    private static final String CGLIB_CLASS_SEPARATOR = "$$";


    /**
     * Map with primitive wrapper type as key and corresponding primitive
     * type as value, for example: Integer.class -> int.class.
     */
    private static final Map<Class<?>, Class<?>> wrapperToPrimitiveTypeMap = new HashMap<Class<?>, Class<?>>(8);

    /**
     * Map with primitive type as key and corresponding wrapper
     * type as value, for example: int.class -> Integer.class.
     */
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new HashMap<Class<?>, Class<?>>(8);

    static {
        wrapperToPrimitiveTypeMap.put(Boolean.class, boolean.class);
        wrapperToPrimitiveTypeMap.put(Byte.class, byte.class);
        wrapperToPrimitiveTypeMap.put(Character.class, char.class);
        wrapperToPrimitiveTypeMap.put(Double.class, double.class);
        wrapperToPrimitiveTypeMap.put(Float.class, float.class);
        wrapperToPrimitiveTypeMap.put(Integer.class, int.class);
        wrapperToPrimitiveTypeMap.put(Long.class, long.class);
        wrapperToPrimitiveTypeMap.put(Short.class, short.class);

        for (Map.Entry<Class<?>, Class<?>> entry : wrapperToPrimitiveTypeMap.entrySet()) {
            primitiveTypeToWrapperMap.put(entry.getValue(), entry.getKey());

        }

    }

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

    public static boolean isAssignableValue(Class<?> type, Object value) {
        Assert.notNull(type, "Type must not be null");
        return (value != null ? isAssignable(type, value.getClass()) : !type.isPrimitive());
    }

    public static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        Assert.notNull(lhsType, "Left-hand side type must not be null");
        Assert.notNull(rhsType, "Right-hand side type must not be null");
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        }
        if (lhsType.isPrimitive()) {
            Class<?> resolvedPrimitive = wrapperToPrimitiveTypeMap.get(rhsType);
            if (resolvedPrimitive != null && lhsType.equals(resolvedPrimitive)) {
                return true;
            }
        }
        else {
            Class<?> resolvedWrapper = primitiveTypeToWrapperMap.get(rhsType);
            if (resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }
        return false;
    }

    public static String convertResourcePathToClassName(String resourcePath){
        Assert.notNull(resourcePath , "Resource path must not be null");
        return resourcePath.replace(PATH_SEPARATOR , PACKAGE_SEPARATOR);
    }
    public static String convertClassNameToResourcePath(String resourcePath){
        Assert.notNull(resourcePath , "Resource path must not be null");
        return resourcePath.replace(PACKAGE_SEPARATOR ,PATH_SEPARATOR);
    }

    public static String getShortName(String className){
        int lastDotIndex = className.lastIndexOf(PACKAGE_SEPARATOR);
        int nameEndIndex = className.lastIndexOf(CGLIB_CLASS_SEPARATOR);
        if(nameEndIndex == -1){
            nameEndIndex = className.length();
        }

        String shortName = className.substring(lastDotIndex + 1, nameEndIndex);
        shortName = shortName.replace(INNER_CLASS_SEPARATOR , PACKAGE_SEPARATOR);
        return shortName;
    }
}
