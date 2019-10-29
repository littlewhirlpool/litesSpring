package org.litespring.core.type.classreading;


import org.litespring.core.io.Resource;
import org.litespring.core.type.AnnotationMetadata;
import org.litespring.core.type.ClassMetadata;
import org.springframework.asm.ClassReader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @program: litespring->SimpleMetadataReader
 * @description: 将创建reader , 调用accept方法封装
 * @author: weizhenfang
 * @create: 2019-10-03 20:52
 **/
public class SimpleMetadataReader implements MetadataReader {

    private final Resource resource;
    // 字节码元数据
    private final ClassMetadata classMetadata;
    // 注解元数据
    private final AnnotationMetadata annotationMetadata;

    /**
     * 构造函数完成封装使用visitor功能
     * @param resource
     * @throws IOException
     */
    public SimpleMetadataReader(Resource resource) throws IOException{
        InputStream is = new BufferedInputStream(resource.getInputStream());
        ClassReader classReader;

        try {
            classReader = new ClassReader(is);
        }finally {
            is.close();
        }

        // 初始化一个visitor
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();
        // 注册给reader
        classReader.accept(visitor , ClassReader.SKIP_DEBUG);

        // 由于visitor继承了ClassMetadataReadingVisitor所以继承了visitor()方法 具有读取类信息的功能
        // visitor自己又重写visitAnnotation方法具有读取注解信息的能力

        this.annotationMetadata = visitor; // 注解数据相关功能  自定义的用来定义对得到的数据的交互
        this.classMetadata = visitor;   // 类数据相关功能  自定义的用来定义对得到的数据的交互
        this.resource = resource;

    }

    public Resource getResource() {
        return this.resource;
    }

    public ClassMetadata getClassMetadata() {
        return this.classMetadata;
    }

    public AnnotationMetadata getAnnotationMetadata() {
        return this.annotationMetadata;
    }


}
