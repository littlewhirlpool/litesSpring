package org.litespring.beans.factory.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.litespring.beans.BeanDefinition;
import org.litespring.beans.ConstructorArgument;
import org.litespring.beans.SimpleTypeConverter;
import org.litespring.beans.factory.BeanCreationException;
import org.litespring.beans.factory.config.ConfigurableBeanFactory;

import java.lang.reflect.Constructor;
import java.util.List;

/**
 *
 * @program: litespring->ConstructorResolver
 * @description: 构造处理器
 * @author: weizhenfang
 * @create: 2019-10-02 23:15
 **/
public class ConstructorResolver {
    protected final Log logger= LogFactory.getLog(getClass());
    private final ConfigurableBeanFactory beanFactory;

    public ConstructorResolver(ConfigurableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    /**
     * 自动装配构造
     * 思路: 参数一致的构造函数才可以使用 , 根据bd中的参数类型使用valueResolver得到对应的对象或者String/Boolean/integer值  传入参数得到实例
     * @param bd
     * @return
     */
    public Object autowireConstructor(final BeanDefinition bd){
        Constructor<?> constructorToUse = null;
        Object[] argsToUse = null;

        Class<?> beanClass = null;
        try {
            // 得到字节码对象
            beanClass  = this.beanFactory.getBeanClassLoader().loadClass(bd.getBeanClassName());
        }catch (ClassNotFoundException e){
            throw new BeanCreationException(bd.getID(),"Instantiation of bean failed , can't resolve class" , e);
        }

        // 候选人 通过字节码对象得到所有的构造函数
        Constructor<?>[] candidates = beanClass.getConstructors();

        // valueResolver 持有beanFactory 可以得到配置映射的bean
        BeanDefinitionValueResolver valueResolver = new BeanDefinitionValueResolver(this.beanFactory);

        // 得到bd中封装好的 constructorArgument
        ConstructorArgument cargs = bd.getConstructorArgument();
        SimpleTypeConverter typeConverter = new SimpleTypeConverter();

        for (int i = 0; i<candidates.length;i++){
            // 得到构造函数的参数列表
            Class<?>[] parameterTypes = candidates[i].getParameterTypes();
            if(parameterTypes.length != cargs.getArgumentCount()){
                // 如果此构造函数的参数列表长度和bd中的不一致 说明不是合适的构造函数 跳过
                continue;
            }
            // 初始化参数列表 作为最后构造对象的参数
            argsToUse = new Object[parameterTypes.length];

            // argsToUse 保存 计算后的数据
            boolean result = this.valueMatchTypes(parameterTypes,
                    cargs.getArgumentValues(),
                    argsToUse,
                    valueResolver,
                    typeConverter);
            if(result){
                constructorToUse = candidates[i];
            }

        }

        // 找不到合适的构造函数
        if(constructorToUse == null){
            throw new BeanCreationException(bd.getID() , "can't find a apporiate constructor");
        }

        try {
            // 构造器注入成功
            return constructorToUse.newInstance(argsToUse);
        }catch (Exception e){
            throw new BeanCreationException(bd.getID() , "can't find a create instance using " + constructorToUse);
        }

    }

    /**
     * 参数type列表 和 参数value列表 是否能匹配
     * @param parameterTypes type列表
     * @param valueHolders value列表
     * @param argsToUse 保存参数值列表
     * @param valueResolver 用来得到ref/value对应的值
     * @param typeConverter 类型转换器 主要是将String类型转化为Integer/Boolean 自定义的类型使用强制类型转化
     * @return
     */
    private boolean valueMatchTypes(Class<?>[] parameterTypes,
                                    List<ConstructorArgument.ValueHolder> valueHolders,
                                    Object[] argsToUse,
                                    BeanDefinitionValueResolver valueResolver,
                                    SimpleTypeConverter typeConverter) {

        for (int i = 0; i < parameterTypes.length; i++) {
            ConstructorArgument.ValueHolder valueHolder = valueHolders.get(i);
            // 获取参数的值 , 可能是TypedStringValue , 也可能是RuntimeBeanReference
            Object originalValue = valueHolder.getValue();

            try {
                // 获取真正的值
                Object resolveredValue = valueResolver.resolveValueIfNecessary(originalValue);
                // 如果参数类型是 int 但是值是字符串 如 "3" 还需要转型
                // 如果转型失败 , 则抛出异常 , 说明这个构造函数不可用
                Object convertedValue = typeConverter.convertIfNecessary(resolveredValue, parameterTypes[i]);
                // 转型成功 , 记录下来
                argsToUse[i] = convertedValue;
            }catch (Exception e){
                logger.error(e);
                return false;
            }
        }
        // 如果所有的parameterType都转型成功 表示这个valueHolders和这个parameterTypes契合
        return true;
    }
}
