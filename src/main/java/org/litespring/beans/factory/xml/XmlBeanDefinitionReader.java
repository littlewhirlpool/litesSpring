package org.litespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.context.annotation.ClassPathBeanDefinitionScanner;
import org.litespring.core.io.Resource;
import org.litespring.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

/**
 * @program: litespring->XmlBeanDefinitionReader
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-28 21:24
 **/
public class XmlBeanDefinitionReader {

    public static final String ID_ATTRIBUTE = "id";
    public static final String CLASS_ATTRIBUTE = "class";
    public static final String SCOPE_ATTRIBUTE = "scope";
    public static final String PROPERTY_ELEMENT = "property";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String VALUE_ATTRIBUTE = "value";
    public static final String NAME_ATTRIBUTE = "name";
    public static final String CONSTRUCTOR_ARG_ELEMENT = "constructor-arg";
    public static final String TYPE_ATTRIBUTE = "type";
    public static final String BEANS_NAMESPACE_URI = "http://www.springframework.org/schema/beans";
    public static final String CONTEXT_NAMESPACE_URI = "http://www.springframework.org/schema/context";
    private static final String BASE_PACKAGE_ATTRIBUTE = "base-package";



    BeanDefinitionRegistry registry;

    protected final Log logger = LogFactory.getLog(getClass());

    public XmlBeanDefinitionReader(BeanDefinitionRegistry registry){
        this.registry = registry;
    }

    /**
     * 得到配置文件的inputStream 解析得到BeanDefinition
     * 再调用registry的registerBeanDefinition注册
     * (这里其实就是注册给了实现Registry接口的DefaultBeanFactory的beanDefinitionMap)
     * @param resource
     */
    public void loadBeanDefinitions(Resource resource) {
        InputStream is = null;
        try {
            is = resource.getInputStream();
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();  //<beans>
            Iterator iter = root.elementIterator();
            while (iter.hasNext()){
                Element ele = (Element) iter.next();

                String namespaceUri = ele.getNamespaceURI();
                if(this.isDefaultNamespace(namespaceUri)){
                    parseDefaultElement(ele); //普通的bean
                } else if(this.isContextNamespace(namespaceUri)){
                    parseComponentElement(ele); //例如<context:component-scan>
                }

            }
        }catch (Exception e){
            throw new BeanDefinitionStoreException("IOException parsing XML doucment" , e);
        }finally {
            if(is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 解析context:component-scan 标签
     * @param ele
     */
    private void parseComponentElement(Element ele) {
        String basePackages = ele.attributeValue(BASE_PACKAGE_ATTRIBUTE);
        ClassPathBeanDefinitionScanner scanner = new ClassPathBeanDefinitionScanner(registry);
        scanner.doScan(basePackages);

    }

    /**
     * 解析bean标签
     * @param ele
     */
    private void parseDefaultElement(Element ele) {
        String id = ele.attributeValue(ID_ATTRIBUTE);
        String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
        BeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
        if(ele.attribute(SCOPE_ATTRIBUTE) != null){
            bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
        }
        // 解析构造配置
        parseConstructorArgElements(ele , bd);
        // 解析属性配置
        parsePropertyElement(ele,bd);
        this.registry.registerBeanDefinition(id , bd);
    }

    public boolean isDefaultNamespace(String namespaceUri) {
        return (!StringUtils.hasLength(namespaceUri) || BEANS_NAMESPACE_URI.equals(namespaceUri));
    }
    public boolean isContextNamespace(String namespaceUri){
        return (!StringUtils.hasLength(namespaceUri) || CONTEXT_NAMESPACE_URI.equals(namespaceUri));
    }

    private void parseConstructorArgElements(Element beanEle,  BeanDefinition bd) {
        Iterator iter = beanEle.elementIterator(CONSTRUCTOR_ARG_ELEMENT);
        while (iter.hasNext()){
            Element ele = (Element) iter.next();
            parseConstructorArgElement(ele,bd);
        }
    }

    /**
     * 解析构造配置
     * 构建对象
     * 加入到bd
     * @param ele
     * @param bd
     */
    private void parseConstructorArgElement(Element ele, BeanDefinition bd) {
        String typeAttr = ele.attributeValue(TYPE_ATTRIBUTE);
        String nameAttr = ele.attributeValue(NAME_ATTRIBUTE);
//        得到一个TypedStringValue / RuntimeBeanReference 对象
        Object value = parsePropertyValue(ele, bd, null);
        ConstructorArgument.ValueHolder valueHolder = new ConstructorArgument.ValueHolder(value);

        if(StringUtils.hasLength(typeAttr)){
            valueHolder.setType(typeAttr);
        }
        if(StringUtils.hasLength(nameAttr)){
            valueHolder.setName(nameAttr);
        }
        // 加入到bd
        bd.getConstructorArgument().addArgumentValue(valueHolder);
    }

    /**
     * 解析属性配置
     * @param beanElem
     * @param bd
     */
    public void parsePropertyElement(Element beanElem , BeanDefinition bd){
        // 得到beanElem的property标签元素的迭代器
        Iterator iter = beanElem.elementIterator(PROPERTY_ELEMENT);
        while (iter.hasNext()){
            Element proElem = (Element) iter.next();
            // 得到property标签元素的name属性值
            String propertyName = proElem.attributeValue(NAME_ATTRIBUTE);
            // 前置校验
            if(!StringUtils.hasLength(propertyName)){
                logger.fatal("Tag 'property' must have a 'name' attribute" );
                return;
            }

            // 得到一个TypedStringValue / RuntimeBeanReference 对象
            Object val = parsePropertyValue(proElem, bd, propertyName);
            // 封装为PropertyValue
            PropertyValue pv = new PropertyValue(propertyName, val);

            // 加入到bd的PropertyValues
            bd.getPropertyValues().add(pv);
        }
    }

    /**
     * 得到一个TypedStringValue / RuntimeBeanReference 对象
     * @param ele
     * @param bd
     * @param propertyName
     * @return
     */
    public Object parsePropertyValue(Element ele , BeanDefinition bd,String propertyName){
        String elementName = (propertyName != null) ?
                "<property> element for property '" + propertyName + "'" : "<constructor-arg> element";
        // 判断是ref/value
        boolean hasRefAttribute = (ele.attribute(REF_ATTRIBUTE)!= null);
        boolean hasValueAttribut = (ele.attribute(VALUE_ATTRIBUTE)!=null);

        if(hasRefAttribute){
            String refName = ele.attributeValue(REF_ATTRIBUTE);
            if(!StringUtils.hasText(refName)){
                logger.error(elementName + " contains empty 'ref' attribute");
            }
            RuntimeBeanReference ref = new RuntimeBeanReference(refName);
            return ref;
        }else if(hasValueAttribut){
            TypedStringValue valueHolder = new TypedStringValue(ele.attributeValue(VALUE_ATTRIBUTE));
            return valueHolder;
        }
        else{
            throw new RuntimeException(elementName + " must specify a ref or value");
        }
    }

}
