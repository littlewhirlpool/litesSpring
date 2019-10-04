package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.core.annotation.AnnotationAttributes;
import org.litespring.core.io.ClassPathResource;
import org.litespring.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.litespring.core.type.classreading.ClassMetadataReadingVisitor;
import org.springframework.asm.ClassReader;

import java.io.IOException;

/**
 * @program: litespring->ClassReaderTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-03 17:38
 **/
public class ClassReaderTest {

    /**
     * 测试ASM效果自定义的Visitor交给reader后 能够被执行重写的方法
     * @throws IOException
     */
	@Test
	public void testGetClasMetaData() throws IOException {
		ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
		ClassReader reader = new ClassReader(resource.getInputStream());

		ClassMetadataReadingVisitor visitor = new ClassMetadataReadingVisitor();

		reader.accept(visitor, ClassReader.SKIP_DEBUG);

		Assert.assertFalse(visitor.isAbstract());
		Assert.assertFalse(visitor.isInterface());
		Assert.assertFalse(visitor.isFinal());
		Assert.assertEquals("org.litespring.service.v4.PetStoreService", visitor.getClassName());
		Assert.assertEquals("java.lang.Object", visitor.getSuperClassName());
		Assert.assertEquals(0, visitor.getInterfaceNames().length);
	}

    /**
     * 测试注解获取功能
     * @throws Exception
     */
    @Test
    public void testGetAnnonation() throws Exception{
        ClassPathResource resource = new ClassPathResource("org/litespring/service/v4/PetStoreService.class");
        // 使用ASM的ClassReader
        ClassReader reader = new ClassReader(resource.getInputStream());

        // 创建继承ClassVisitor父类的自定义的visitor对象
        AnnotationMetadataReadingVisitor visitor = new AnnotationMetadataReadingVisitor();

        // 将visitor通过accept方法传递给reader
        reader.accept(visitor, ClassReader.SKIP_DEBUG);


        String annotation = "org.litespring.stereotype.Component";
        Assert.assertTrue(visitor.hasAnnotation(annotation));

        AnnotationAttributes attributes = visitor.getAnnotationAttributes(annotation);

        Assert.assertEquals("petStore", attributes.get("value"));

    }

}
