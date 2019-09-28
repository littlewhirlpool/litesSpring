package org.litespring.beans.factory.support;

import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.BeanDefinitionStoreException;
import org.litespring.beans.factory.BeanFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.litespring.beans.BeanDefinition;
import org.litespring.util.ClassUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @program: litespring->DefaultBeanFactory
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-24 21:07
 **/
public class DefaultBeanFactory implements BeanFactory {

    public static final String ID_ATTRIBUTE = "id";

    public static final String CLASS_ATTRIBUTE = "class";

    // 类定义的id-定义 键值对  map  注意键值对的类型
    private final Map<String , BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String, BeanDefinition>();


    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    /**
     * 加载类的定义
     * @param configFile 文件名 根路径
     */
    private void loadBeanDefinition(String configFile) {
        InputStream is = null;
        try {
            // 通过classloader 得到配置文件
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);

            // 使用dom4j 读取配置文件
            SAXReader reader = new SAXReader();
            Document doc = reader.read(is);

            Element root = doc.getRootElement();
            Iterator iter = root.elementIterator();

            // 遍历beans 标签下的子标签 bean标签
            while (iter.hasNext()){
                Element ele = (Element) iter.next();  //得到element
                String id = ele.attributeValue(ID_ATTRIBUTE);   //得到id属性值
                String beanClassName = ele.attributeValue(CLASS_ATTRIBUTE);  //得到class属性值
                BeanDefinition bd = new GenericBeanDefinition(id,beanClassName);  //构建一个类的定义类对象
                this.beanDefinitionMap.put(id,bd);  // 将定义类对象放到Factory的beanDefinitionMap中去 id为key
            }

        } catch (DocumentException e) {
            throw new BeanDefinitionStoreException("IOExcepton parsing XML document" , e);
        }finally {
            if(is != null){
                try{
                    is.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }

        }
    }

    /**
     * 根据类的id得到类的定义对象
     * @param beanID
     * @return
     */
    public BeanDefinition getBeanDefinition(String beanID) {
        return this.beanDefinitionMap.get(beanID);
    }

    /**
     * 根据beanid 返回一个bean实例
     * @param beanID
     * @return
     */
    public Object getBean(String beanID) {
        BeanDefinition bd = this.getBeanDefinition(beanID);  //通过id得到类的定义对象
        if (bd == null){
            throw new BeanCreationException("Bean Definition does not exist");
        }

        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        String beanClassName = bd.getBeanClassName();  //通过类的定义对象得到类的classname

        try {
            Class<?> clz = cl.loadClass(beanClassName);  //使用类的加载器 加载类的字节码对象
            return clz.newInstance(); //反射得到实例
        } catch (Exception e) {
            throw new BeanCreationException("create bean for" + beanClassName + " faild" , e);
        }
    }
}
