package com.video.service;

import com.video.exception.MinioException;
import io.minio.MinioClient;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Path;
import java.time.Duration;

@Service
public class MinioService implements InitializingBean {

    private MinioClient minioClient;

    @Value("${minio.bucket}")
    private String bucket;
    @Value("${minio.url}")
    private String url;
    @Value("${minio.access-key}")
    private String accessKey;
    @Value("${minio.secret-key}")
    private String secretKey;
    private boolean secure = false;
    private Duration connectTimeout = Duration.ofSeconds(10);
    private Duration writeTimeout = Duration.ofSeconds(60);
    private Duration readTimeout = Duration.ofSeconds(10);

    /**
     * 获取文件流
     *
     * @param path
     * @return
     * @throws MinioException
     */
    public InputStream get(String path) throws MinioException {
        try {
            return minioClient.getObject(bucket, path);
        } catch (Exception e) {
            throw new MinioException("下载文件出错", e);
        }
    }

    /**
     * 获取文件流
     *
     * @param path
     * @return
     * @throws MinioException
     */
    public InputStream get(Path path) throws MinioException {
        try {
            return minioClient.getObject(bucket, path.toString());
        } catch (Exception e) {
            throw new MinioException("下载文件出错", e);
        }
    }

    /**
     * 上传文件
     *
     * @param path
     * @param file
     * @param contentType
     * @throws MinioException
     */
    public void upload(String path, InputStream file, String contentType) throws MinioException {
        try {
            minioClient.putObject(bucket, path, file, contentType);
        } catch (Exception e) {
            throw new MinioException("上传文件出错", e);
        }
    }

    /**
     * 上传文件
     *
     * @param path
     * @param file
     * @param contentType
     * @throws MinioException
     */
    public void upload(Path path, InputStream file, String contentType) throws MinioException {
        try {
            minioClient.putObject(bucket, path.toString(), file, contentType);
        } catch (Exception e) {
            throw new MinioException("上传文件出错", e);
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @throws MinioException
     */
    public void delete(Path path) throws MinioException {
        try {
            minioClient.removeObject(bucket, path.toString());
        } catch (Exception e) {
            throw new MinioException("删除文件出错", e);
        }
    }

    /**
     * 删除文件
     *
     * @param path
     * @throws MinioException
     */
    public void delete(String path) throws MinioException {
        try {
            minioClient.removeObject(bucket, path);
        } catch (Exception e) {
            throw new MinioException("删除文件出错", e);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        try {
            minioClient = new MinioClient(url, accessKey, secretKey, secure);
            minioClient.setTimeout(connectTimeout.toMillis(), writeTimeout.toMillis(), readTimeout.toMillis());
        } catch (Exception e) {
            throw new MinioException("初始化Minio出错，请检查cm.minio配置项", e);
        }
    }
}
