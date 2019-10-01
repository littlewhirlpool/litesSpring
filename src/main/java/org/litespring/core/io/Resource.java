package org.litespring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * 抽象出的对配置文件的获取
 */
public interface Resource {

    InputStream getInputStream() throws IOException;
    String getDescription();
}
