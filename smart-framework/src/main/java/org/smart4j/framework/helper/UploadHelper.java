package org.smart4j.framework.helper;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.framework.bean.FileParam;
import org.smart4j.framework.bean.FormParam;
import org.smart4j.framework.bean.Param;
import org.smart4j.framework.util.CollectionUtil;
import org.smart4j.framework.util.FileUtil;
import org.smart4j.framework.util.StreamUtil;
import org.smart4j.framework.util.StringUtil;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 文件上传助手类
 */
public final class UploadHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(UploadHelper.class);

    private static ServletFileUpload servletFileUpload;

    /**
     * 初始化
     */
    public static void init(final ServletContext servletContext) {
        final File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
        servletFileUpload = new ServletFileUpload(
                new DiskFileItemFactory(DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, repository));
        final int uploadLimit = ConfigHelper.getAppUploadLimit();
        if (uploadLimit != 0) {
            servletFileUpload.setFileSizeMax(uploadLimit * 1024 * 1024);
        }
    }

    /**
     * 判断请求是否为 multipart 类型
     */
    public static boolean isMultipart(final HttpServletRequest request) {
        return ServletFileUpload.isMultipartContent(request);
    }

    /**
     * 创建请求对象
     */
    public static Param createParam(final HttpServletRequest request) throws IOException {
        final List<FormParam> formParamList = new ArrayList<FormParam>();
        final List<FileParam> fileParamList = new ArrayList<FileParam>();
        try {
            final Map<String, List<FileItem>> fileItemListMap = servletFileUpload.parseParameterMap(
                    request);
            if (CollectionUtil.isNotEmpty(fileItemListMap)) {
                for (final Map.Entry<String, List<FileItem>> fileItemListEntry : fileItemListMap
                        .entrySet()) {
                    final String fieldName = fileItemListEntry.getKey();
                    final List<FileItem> fileItemList = fileItemListEntry.getValue();
                    if (CollectionUtil.isNotEmpty(fileItemList)) {
                        for (final FileItem fileItem : fileItemList) {
                            if (fileItem.isFormField()) {
                                final String fieldValue = fileItem.getString("UTF-8");
                                formParamList.add(new FormParam(fieldName, fieldValue));
                            } else {
                                final String fileName = FileUtil.getRealFileName(
                                        new String(fileItem.getName().getBytes(), "UTF-8"));
                                if (StringUtil.isNotEmpty(fileName)) {
                                    final long fileSize = fileItem.getSize();
                                    final String contentType = fileItem.getContentType();
                                    final InputStream inputSteam = fileItem.getInputStream();
                                    fileParamList.add(new FileParam(fieldName, fileName, fileSize,
                                            contentType, inputSteam));
                                }
                            }
                        }
                    }
                }
            }
        } catch (final FileUploadException e) {
            LOGGER.error("create param failure", e);
            throw new RuntimeException(e);
        }
        return new Param(formParamList, fileParamList);
    }

    /**
     * 上传文件
     */
    public static void uploadFile(final String basePath, final FileParam fileParam) {
        try {
            if (fileParam != null) {
                final String filePath = basePath + fileParam.getFileName();
                FileUtil.createFile(filePath);
                final InputStream inputStream = new BufferedInputStream(fileParam.getInputStream());
                final OutputStream outputStream = new BufferedOutputStream(
                        new FileOutputStream(filePath));
                StreamUtil.copyStream(inputStream, outputStream);
            }
        } catch (final Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量上传文件
     */
    public static void uploadFile(final String basePath, final List<FileParam> fileParamList) {
        try {
            if (CollectionUtil.isNotEmpty(fileParamList)) {
                for (final FileParam fileParam : fileParamList) {
                    uploadFile(basePath, fileParam);
                }
            }
        } catch (final Exception e) {
            LOGGER.error("upload file failure", e);
            throw new RuntimeException(e);
        }
    }
}
