package org.litespring.beans.factory.annotation;

import org.litespring.beans.BeansException;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.AutowireCapableBeanFactory;
import org.litespring.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.litespring.core.annotation.AnnotationUtils;
import org.litespring.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

/**
 * @program: litespring->AutowiredAnnotationProcessor
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-06 17:06
 **/
public class AutowiredAnnotationProcessor implements InstantiationAwareBeanPostProcessor {

    private AutowireCapableBeanFactory beanFactory;
    private String requireParameterName = "required";
    private boolean requiredParameterValue = true;

    private final Set<Class<? extends Annotation>> autowiredAnnotationTypes
            = new LinkedHashSet<Class<? extends Annotation>>();


    /**
     * 构造函数
     * 初始化autowiredAnnotationTypes 增加Autowired.class 字节码对象 表示有这个注解的字段是需要自动注入的
     */
    public AutowiredAnnotationProcessor(){
        this.autowiredAnnotationTypes.add(Autowired.class);
    }

    /**
     * 
     * @param clazz
     * @return
     */
    public InjectionMetadata buildAutowiringMetadata(Class<?> clazz){
        LinkedList<InjectionElement> elements
                = new LinkedList();
        // 新建局部变量targetClass 表示要构建<注入数据对象>的目标字节码对象
        Class  targetClass = clazz;

        do {
            LinkedList<InjectionElement> currElements = new LinkedList<InjectionElement>();
            // 遍历targetClass所有的字段
            for (Field field : targetClass.getDeclaredFields()) {
                // 找到该字段的自动注入的注解
                Annotation ann = findAutowiredAnnotation(field);
                // 如果注解非空表示该字段有表示自动注入的注解 表示该字段是需要自动注入的字段
                if (ann != null){
                    if (Modifier.isStatic(field.getModifiers())){
                        continue;
                    }
                    // 获取required
                    boolean required = determineRequiredStatus(ann);
                    // 如果该字段有表示自动注入的注解 就把该字段封装进AutowiredFieldElement 再 add 到elements里
                    currElements.add(new AutowiredFieldElement(field , required,beanFactory));
                }
            }
            for (Method method : targetClass.getDeclaredMethods()) {
                // TODO 处理方法注入
            }
            elements.addAll(0 , currElements);
            targetClass = targetClass.getSuperclass();
        }
        while (targetClass != null && targetClass != Object.class);
        InjectionMetadata injectionMetadata = new InjectionMetadata(clazz, elements);
        return injectionMetadata;
    }

    private boolean determineRequiredStatus(Annotation ann) {
        try {
            Method method = ReflectionUtils.findMethod(ann.annotationType(), this.requireParameterName);
            if(method == null){
                // Annotations like @Inject and @Value don't have a method (attribute) named "required"
                // -> default to required status
                return true;
            }
            return (this.requiredParameterValue == (Boolean)
                    ReflectionUtils.invokeMethod(method , ann));
        }catch (Exception ex){
            // An exception was thrown during reflective invocation of the required attribute
            // -> default to required status
            return true;
        }
    }


    private Annotation findAutowiredAnnotation(AccessibleObject ao) {
        //  遍历 this.autowiredAnnotationTypes 此属性会在构造函数中初始化 表示自动注入的注解有哪些
        for (Class<? extends Annotation> type : this.autowiredAnnotationTypes) {
            Annotation ann = AnnotationUtils.getAnnotation(ao, type);
            if(ann != null){
                return ann;
            }
        }
        return null;
    }

    public void setBeanFactory(AutowireCapableBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }


    public Object beforeInitialization(Object bean, String beanName) throws BeansException {
        //do nothing
        return bean;
    }
    public Object afterInitialization(Object bean, String beanName) throws BeansException {
        // do nothing
        return bean;
    }
    public Object beforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        return null;
    }

    public boolean afterInstantiation(Object bean, String beanName) throws BeansException {
        // do nothing
        return true;
    }

    public void postProcessPropertyValues(Object bean, String beanName) throws BeansException {
        InjectionMetadata metadata = buildAutowiringMetadata(bean.getClass());
        try {
            metadata.inject(bean);
        }
        catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of autowired dependencies failed", ex);
        }
    }
}
