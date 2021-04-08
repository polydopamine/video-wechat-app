package com.video.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wangsihang
 * @date 2021/3/26 下午9:18
 */
public class BaseController {

    @Autowired
    public RedisTemplate redisTemplate;

    public static final String USER_REDIS_SESSION = "user-redis-session";


}
