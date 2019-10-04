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
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-03 18:31
 **/
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor implements AnnotationMetadata /*implements  AnnotationMetadata */{
    private final Set<String> annotationSet = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<String, AnnotationAttributes>(4);

    public AnnotationMetadataReadingVisitor(){}

    /**
     * 重写父类方法
     * @param desc 将会由classReader对象调用此方法 传入读到的类的信息
     * @param visible
     * @return AnnotationVisitor
     */
    public AnnotationVisitor visitAnnotation(final String desc , boolean visible){
        String className = Type.getType(desc).getClassName();
        // 加入到annotationSet
        this.annotationSet.add(className);
        // 通过构造函数将attributeMap传递给 AnnotationVisitor 的子类 构造方法中又共享将此map赋值给了,
        // 此子类实现的一些方法会被调用可以操作attributeMap进而改变了attributeMap的值
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
