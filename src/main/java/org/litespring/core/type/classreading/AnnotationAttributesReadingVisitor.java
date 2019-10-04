package org.litespring.core.type.classreading;

import org.litespring.core.annotation.AnnotationAttributes;
import org.springframework.asm.AnnotationVisitor;
import org.springframework.asm.SpringAsmInfo;

import java.util.Map;

/**
 * @program: litespring->AnnotationAttributesReadingVisitor
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-03 20:03
 **/
public class AnnotationAttributesReadingVisitor extends AnnotationVisitor {
    private final String annotationType;

    // 注意 value是attributes 该值通过构造传入的map赋值 , 然后将自己的map也交给其(如果传入的map是一个对象的属性
    // 那么就可以通过对象得到所有注解的 type-attributes 键值对
    private final Map<String, AnnotationAttributes> attributesMap;

    // 该注解的所有属性值 (map子类)
    AnnotationAttributes attributes = new AnnotationAttributes();


    public AnnotationAttributesReadingVisitor (
            String annotationType, Map<String, AnnotationAttributes> attributesMap) {
        super(SpringAsmInfo.ASM_VERSION);

        this.annotationType = annotationType;
        this.attributesMap = attributesMap;

    }

    /**
     * 某一个注解完全查看结束
     */
    @Override
    public final void visitEnd(){
        // 将属性map attributes 存放到 attributesMap 此attributesMap来自构造函数 存放的是所有的注解的属性s ( 注解 - 注解属性s)
        this.attributesMap.put(this.annotationType, this.attributes);
    }

    /**
     * 封装注解的属性键值对 例如 @Component(value = "petStore") 中的value = "petStore"
     * 一个注解有多少个键值对就有可能被调用多少次
     * @param attributeName
     * @param attributeValue
     */
    public void visit(String attributeName, Object attributeValue) {
        this.attributes.put(attributeName, attributeValue);
    }


}
