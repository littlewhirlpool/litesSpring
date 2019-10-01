package org.litespring.beans.factory.xml;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.support.BeanDefinitionRegistry;
import org.litespring.beans.factory.support.GenericBeanDefinition;
import org.litespring.core.io.Resource;

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

    BeanDefinitionRegistry registry;

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

}
