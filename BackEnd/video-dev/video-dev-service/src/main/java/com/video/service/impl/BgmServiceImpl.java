package com.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.Bgm;
import com.video.mapper.BgmMapper;
import com.video.service.IBgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author wangsihang
 * @date 2021/4/12 下午10:18
 */
@Service
public class BgmServiceImpl implements IBgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> getBgmList() {
        return bgmMapper.selectList(new QueryWrapper<>());
    }
}
