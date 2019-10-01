package org.litespring.beans.factory.xml;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.PropertyValue;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.config.RuntimeBeanReference;
import org.litespring.beans.factory.config.TypedStringValue;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
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
                String id = ele.attributeValue(ID_ATTRIBUTE);
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);
                GenericBeanDefinition bd = new GenericBeanDefinition(id, beanClassName);
                if(ele.attribute(SCOPE_ATTRIBUTE) != null){
                    bd.setScope(ele.attributeValue(SCOPE_ATTRIBUTE));
                }
                parsePropertyElement(ele,bd);
                this.registry.registerBeanDefinition(id , bd);

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
