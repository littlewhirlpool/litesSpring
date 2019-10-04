package org.litespring.core.io;

import org.litespring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @program: litespring->ClassPathResource
 * @description:
 * @author: weizhenfang
 * @create: 2019-09-29 23:24
 **/
public class ClassPathResource implements Resource {

    private String path;
    private ClassLoader classLoader;

    // ClassPathResource通过构造函数将类路径传入 , 调用getResourceAsStream方法是通过类的加载器的getResourceAsStream返回字节流
    public ClassPathResource(String path) {
        this(path , (ClassLoader)null);
    }

    public ClassPathResource(String path,ClassLoader classLoader) {
        this.path = path;
        this.classLoader = (classLoader != null ? classLoader : ClassUtils.getDefaultClassLoader());
    }

    public InputStream getInputStream() throws IOException {
        InputStream is = this.classLoader.getResourceAsStream(this.path);

        if(is == null){
            // 这里要抛出异常来表示功能未能完成
            throw new FileNotFoundException(path + " cannt be opend");
        }
        return  is;
    }

    public String getDescription() {
        return this.path;
    }
}
