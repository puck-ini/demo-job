package org.zchzh.file.entity;

import java.io.InputStream;

/**
 * @author zengchzh
 * @date 2022/1/14
 */
public interface StorageFile {


    InputStream getInputStream();


    String getMd5();
}
