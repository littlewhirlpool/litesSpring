package org.litespring.core.type.classreading;


import org.litespring.core.annotation.AnnotationAttributes;
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
public class AnnotationMetadataReadingVisitor extends ClassMetadataReadingVisitor  /*implements  AnnotationMetadata */{
    private final Set<String> annotationSet = new LinkedHashSet<String>(4);
    private final Map<String, AnnotationAttributes> attributeMap = new LinkedHashMap<String, AnnotationAttributes>(4);

    public AnnotationMetadataReadingVisitor(){}

    /**
     * 重写父类方法
     * @param desc
     * @param visible
     * @return AnnotationVisitor
     */
    public AnnotationVisitor visitAnnotation(final String desc , boolean visible){
        String className = Type.getType(desc).getClassName();
        // 加入到annotationSet
        this.annotationSet.add(className);
        // attributeMap
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
