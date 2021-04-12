package com.video.service;

import com.video.entity.Bgm;

import java.util.List;

/**
 * @author wangsihang
 * @date 2021/4/12 下午10:18
 */
public interface IBgmService {

    /**
     * 获取背景音乐列表
     * @return 背景音乐列表
     */
    List<Bgm> getBgmList();
}
