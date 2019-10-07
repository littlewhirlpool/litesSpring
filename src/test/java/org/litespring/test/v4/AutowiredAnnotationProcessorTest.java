package org.litespring.test.v4;

import org.junit.Assert;
import org.junit.Test;
import org.litespring.beans.factory.annotation.AutowiredAnnotationProcessor;
import org.litespring.beans.factory.annotation.AutowiredFieldElement;
import org.litespring.beans.factory.annotation.InjectionElement;
import org.litespring.beans.factory.annotation.InjectionMetadata;
import org.litespring.beans.factory.config.DependencyDescriptor;
import org.litespring.beans.factory.support.DefaultBeanFactory;
import org.litespring.dao.v4.AccountDao;
import org.litespring.dao.v4.ItemDao;
import org.litespring.service.v4.PetStoreService;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @program: litespring->AutowiredAnnotationProcessorTest
 * @description:
 * @author: weizhenfang
 * @create: 2019-10-06 16:09
 **/
public class AutowiredAnnotationProcessorTest {
    AccountDao accountDao = new AccountDao();
    ItemDao itemDao = new ItemDao();
    DefaultBeanFactory beanFactory = new DefaultBeanFactory(){
        // 匿名内部类 重写resolveDependency 直接返回bean 类似Mock功能 这里只是为了展示这个用法 替换为常规的使用方式也可以
        public Object resolveDependency(DependencyDescriptor descriptor){
            if(descriptor.getDependencyType().equals(AccountDao.class)){
                return accountDao;
            }
            if(descriptor.getDependencyType().equals(ItemDao.class)){
                return itemDao;
            }
            throw new RuntimeException("can't support types except AccountDao and ItemDao");
        }
    };


    /**
     * 测试AutowiredAnnotationProcessor 获取一个类InjectionMetadata 的能力
     */
    @Test
    public void testGetInjectionMetadata(){

        // 创建AutowireAnnotationProcessor 对象
        AutowiredAnnotationProcessor processor = new AutowiredAnnotationProcessor();
        // 给processor设置factory
        processor.setBeanFactory(beanFactory);
        // 使用processor构建PetStoreService的注入数据对象InjectionMetadata
        InjectionMetadata injectionMetadata = processor.buildAutowiringMetadata(PetStoreService.class);
        // 校验结果
        List<InjectionElement> elements = injectionMetadata.getInjectionElements();
        Assert.assertEquals(2, elements.size());

        // 断言是否拥有某个属性
        assertFieldExists(elements,"accountDao");
        assertFieldExists(elements,"itemDao");

        PetStoreService petStore = new PetStoreService();

        // 测试注入功能 这个是上一步实现的功能 这里继续测试 可以使得测试更健壮
        injectionMetadata.inject(petStore);

        Assert.assertTrue(petStore.getAccountDao() instanceof AccountDao);

        Assert.assertTrue(petStore.getItemDao() instanceof ItemDao);
    }

    private void assertFieldExists(List<InjectionElement> elements ,String fieldName){
        for(InjectionElement ele : elements){
            AutowiredFieldElement fieldEle = (AutowiredFieldElement)ele;
            Field f = fieldEle.getField();
            if(f.getName().equals(fieldName)){
                return;
            }
        }
        Assert.fail(fieldName + "does not exist!");
    }



}
