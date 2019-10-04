package org.litespring.core.type.classreading;


import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.type.AnnotationMetadata;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.Type;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @program: litespring->AnnotationMetadataReadingVisitor
 * @description: 注解元数据读取visitor
 * @author: weizhenfang
 * @create: 2019-10-03 18:31
 **/
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata /*implements  AnnotationMetadata */{
    private final Set<String> annotationSet = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<String, AnnotationAttributes>(4);

    public AnnotationMetadataReadingVisitor(){}

    /**
     * 重写父类方法 , reader每读取到一个注解就会调用此接口 , 并且此方法的返回值交给了reader reader解析每一个的注解的详细
     * 信息如属性键值对将在调用返回值重写的方法传递给返回值
     * @param desc 将会由classReader对象调用此方法 传入读到的类的信息
     * @param visible
     * @return AnnotationVisitor 这个对象返回给reader reader将会和其交互
     */
    public AnnotationVisitor visitAnnotation(final String desc , boolean visible){
        String className = Type.getType(desc).getClassName();
        // 加入到annotationSet
        this.annotationSet.add(className);
        // 通过构造函数将attributeMap传递给 AnnotationVisitor 的子类 构造方法中又共享将此map赋值给了自己的map,
        // 这样其实两个对象是共同 维护同一个map的
        // 当前方法其实是被reader调用 , 这个返回值重写的AnnotationVisitor的方法visit visitEnd 方法同样也会被调用
        // reader将读取好的信息通过方法传递给这个返回值对象,返回值对象将信息存放到共同维护的map中 传递回来
        return new AnnotationAttributesReadingVisitor(className,this.attributeMap);
    }

    public Set<String> getAnnotationTypes() {
        return this.annotationSet;
    }

    public boolean hasAnnotation(String annotationType) {
        return this.annotationSet.contains(annotationType);
    }

    public AnnotationAttributes getAnnotationAttributes(String annotationType) {
        return this.attributeMap.get(annotationType);
    }


}
