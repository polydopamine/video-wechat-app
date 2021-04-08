package com.video.service;

import com.video.entity.Users;
import com.video.utils.ResultBean;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangsihang
 * @date 2021/3/23 下午10:16
 */
public interface IUserService {

    /**
     * 判断用户名是否已存在
     * @param username 用户名
     * @return boolean
     */
    boolean queryUsernameIsExist(String username);

    /**
     * 新增用户
     * @param user 用户信息
     */
    void saveUser(Users user);

    /**
     * 用户登录-校验用户
     * @param username 用户名
     * @param password 密码
     * @return
     */
    Users queryUserForLogin(String username, String password);

    /**
     * 用户更改头像
     * @param userId 用户id
     * @param file 用户头像
     */
    void updateFace(String userId, MultipartFile file);
}
