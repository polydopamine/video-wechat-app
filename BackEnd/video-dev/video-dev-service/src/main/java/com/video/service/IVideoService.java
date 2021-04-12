package com.video.service;

import com.video.exception.MinioException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wangsihang
 * @date 2021/4/12 下午10:18
 */
public interface IVideoService {

    /**
     * 发布视频
     * @param userId 用户id
     * @param audioId 背景音乐id
     * @param videoDesc 视频描述
     * @param videoSeconds 视频长度(秒)
     * @param videoWidth 视频高度
     * @param videoHeight 视频宽度
     * @param coverPath 视频封面
     * @param file 视频文件
     */
    void uploadVideo(String userId, String audioId, String videoDesc, Float videoSeconds,
                     Integer videoWidth, Integer videoHeight, String coverPath, MultipartFile file);
}
