package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 这个抽象出的接口很经典 , 提取聚合需要对文件的操作
 * 抽象出的对配置文件的获取
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
    String getDescription();
}
