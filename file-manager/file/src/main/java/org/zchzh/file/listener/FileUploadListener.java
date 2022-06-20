package org.zchzh.file.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.fileupload.ProgressListener;

/**
 * @author zengchzh
 * @date 2022/6/20
 */

@Slf4j
public class FileUploadListener implements ProgressListener {
    @Override
    public void update(long pBytesRead, long pContentLength, int pItems) {
        log.info("pBytesRead = " + pBytesRead + ", pContentLength = " + pContentLength + ", pItems = " + pItems);
    }
}
