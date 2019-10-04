package org.litespring.context.annotation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.BeanNameGenerator;
import org.litespring.core.io.Resource;
import org.litespring.core.io.support.PackageResourceLoader;
import org.litespring.core.type.classreading.MetadataReader;
import org.litespring.core.type.classreading.SimpleMetadataReader;
import org.litespring.stereotype.Component;
import org.litespring.util.StringUtils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * @program: litespring->ClassPathBeanDefinitionScanner
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-04 14:57
 **/
public class ClassPathBeanDefinitionScanner {


    private final BeanDefinitionRegistry registry;

    private PackageResourceLoader resourceLoader = new PackageResourceLoader();

    protected final Log logger = LogFactory.getLog(getClass());

    private BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    public Set<BeanDefinition> doScan(String packagesToScan) {

        String[] basePackages = StringUtils.tokenizeToStringArray(packagesToScan,",");

        Set<BeanDefinition> beanDefinitions = new LinkedHashSet<BeanDefinition>();
        for (String basePackage : basePackages) {
            // 得到包下带有@Component注解的类
            Set<BeanDefinition> candidates = findCandidateComponents(basePackage);
            for (BeanDefinition candidate : candidates) {
                beanDefinitions.add(candidate);
                // 注册给factory
                registry.registerBeanDefinition(candidate.getID(),candidate);
            }
        }
        return beanDefinitions;
    }


    /**
     * 得到包下带有@Component注解的类 并将其转化为ScannedGenericBeanDefinition
     * 1.使用PackageResourceLoader得到resource
     * 2.使用SimpleMetadataReader得到metadataReader
     * 3.使用ScannedGenericBeanDefinition封装扫描到的类的定义
     * @param basePackage
     * @return
     */
    public Set<BeanDefinition> findCandidateComponents(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<BeanDefinition>();
        try {

            // 使用PackageResourceLoader得到resource
            Resource[] resources = this.resourceLoader.getResources(basePackage);

            // 这里的resource是字节码文件 不是 xml配置文件
            for (Resource resource : resources) {
                try {

                    // 使用SimpleMetadataReader得到metadataReader
                    MetadataReader metadataReader = new SimpleMetadataReader(resource);

                    if(metadataReader.getAnnotationMetadata().hasAnnotation(Component.class.getName())){
                        // 有Component注解
                        // 使用ScannedGenericBeanDefinition封装扫描到的类的定义
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader.getAnnotationMetadata());
                        // 得到beanName (思路: 先看注解中是否定义 没有就使用类名首字母变小写
                        String beanName = this.beanNameGenerator.generateBeanName(sbd, this.registry);
                        sbd.setId(beanName);
                        candidates.add(sbd);
                    }
                }
                catch (Throwable ex) {
                    throw new BeanDefinitionStoreException(
                            "Failed to read candidate component class: " + resource, ex);
                }

            }
        }
        catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }

}
