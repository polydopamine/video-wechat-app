package com.video.controller;

import com.video.service.IVideoService;
import com.video.utils.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangsihang
 * @date 2021/4/12 下午10:18
 */
@RestController
@RequestMapping("video")
public class VideoController {

    @Autowired
    private IVideoService videoService;

    @PostMapping("upload")
    public ResultBean upload(String userId, String audioId, String videoDesc, Float videoSeconds,
                             Integer videoWidth, Integer videoHeight, String coverPath, MultipartFile file) {
        if (StringUtils.isBlank(userId) || videoSeconds == null || videoWidth == null || videoHeight == null || file == null) {
            return ResultBean.errorMsg("参数错误");
        }
        videoService.uploadVideo(userId, audioId, videoDesc, videoSeconds, videoWidth, videoHeight, coverPath, file);
        return ResultBean.ok();
    }

    public ResultBean uploadCover(String userId, String videoId, MultipartFile file) {
        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return ResultBean.errorMsg("参数错误");
        }
        videoService.uploadCover(userId, videoId, file);
        return ResultBean.ok();
    }

}
