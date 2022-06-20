package org.zchzh.file.config;

import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUpload;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.zchzh.file.listener.FileUploadListener;

/**
 * @author zengchzh
 * @date 2022/6/20
 */
public class TestCommonsMultipartResolver extends CommonsMultipartResolver {

    @Override
    protected FileUpload newFileUpload(FileItemFactory fileItemFactory) {
        FileUpload fileUpload = super.newFileUpload(fileItemFactory);
        fileUpload.setProgressListener(new FileUploadListener());
        return fileUpload;
    }
}
