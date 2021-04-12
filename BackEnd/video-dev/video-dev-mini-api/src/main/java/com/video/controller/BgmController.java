package com.video.controller;

import com.video.entity.Bgm;
import com.video.service.IBgmService;
import com.video.utils.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author wangsihang
 * @date 2021/4/12 下午10:17
 */
@RestController
@RequestMapping("bgm")
public class BgmController {

    @Autowired
    private IBgmService bgmService;

    @GetMapping("list")
    public ResultBean getBgmList() {
        List<Bgm> bgmList = bgmService.getBgmList();
        return ResultBean.ok(bgmList);
    }
}
