package com.video.vo;

import com.video.entity.Users;
import lombok.Data;

/**
 * @author wangsihang
 * @date 2021/3/26 下午9:23
 */
@Data
public class UsersVo extends Users {

    /**
     * 用户token
     */
    private String userToken;
}
