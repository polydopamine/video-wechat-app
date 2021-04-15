package com.video.service.impl;

import com.video.entity.Videos;
import com.video.exception.MinioException;
import com.video.mapper.VideosMapper;
import com.video.service.IVideoService;
import com.video.service.MinioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * @author wangsihang
 * @date 2021/4/12 下午10:19
 */
@Service
@Slf4j
public class VideoServiceImpl implements IVideoService {

    @Autowired
    private VideosMapper videosMapper;
    @Autowired
    private MinioService minioService;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void uploadVideo(String userId, String audioId, String videoDesc, Float videoSeconds, Integer videoWidth, Integer videoHeight, String coverPath, MultipartFile file) {
        // 上传视频到minio服务器
        String path = "video/" + userId + "/" + file.getOriginalFilename();
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
        } catch (MinioException e) {
            e.printStackTrace();
            log.error("视频上传异常, [{}]", "userId = " + userId, e);
            throw new RuntimeException("视频上传异常");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("视频解析异常, [{}]", "userId = " + userId, e);
            throw new RuntimeException("视频解析异常");
        }
        // 新增video数据至db
        Videos videos = new Videos();
        videos.setUserId(userId);
        videos.setAudioId(audioId);
        videos.setVideoDesc(videoDesc);
        videos.setVideoPath(path);
        videos.setVideoSeconds(videoSeconds);
        videos.setVideoWidth(videoWidth);
        videos.setVideoHeight(videoHeight);
        videos.setCoverPath(path);
        videos.setLikeCounts(0L);
        videos.setStatus(1);
        videos.setCreateTime(LocalDateTime.now());
        videosMapper.insert(videos);
    }

    @Override
    public void uploadCover(String userId, String videoId, MultipartFile file) {
        // 上传视频到minio服务器
        String path = "video/" + userId + "/" + file.getOriginalFilename();
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
        } catch (MinioException e) {
            e.printStackTrace();
            log.error("封面上传异常, [{}]", "id = " + userId + ", videoId = " + videoId, e);
            throw new RuntimeException("封面上传异常");
        } catch (IOException e) {
            e.printStackTrace();
            log.error("封面解析异常, [{}]", "id = " + userId + ", videoId = " + videoId, e);
            throw new RuntimeException("封面解析异常");
        }
        Videos updateVideo = new Videos();
        updateVideo.setId(videoId);
        updateVideo.setCoverPath(path);
        videosMapper.updateById(updateVideo);
    }
}
