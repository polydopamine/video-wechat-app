package com.video.controller;

import com.video.entity.Users;
import com.video.service.IUserService;
import com.video.utils.MD5Utils;
import com.video.utils.ResultBean;
import com.video.vo.UsersVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangsihang
 * @date 2021/3/23 下午10:08
 */
@RestController
//@Api("用户注册和登录接口")
@RequestMapping("user")
public class RegisterLoginController extends BaseController {

    @Autowired
    private IUserService userService;

    @ApiOperation(value = "用户注册", notes = "用于用户注册的接口")
    @PostMapping("register")
    public ResultBean register(@RequestBody Users user) throws Exception {
        // judge username and password is not null
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return ResultBean.errorMsg("用户名和密码不能为空");
        }
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        if (usernameIsExist) {
            return ResultBean.errorMsg("该用户名已存在,请重新选择");
        } else {
            user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            user.setNickname(user.getUsername());
            user.setFansCounts(0);
            user.setFollowCounts(0);
            user.setReceiveLikeCounts(0);
            userService.saveUser(user);
        }
        user.setPassword(null);
        UsersVo usersVo = setUserRedisVo(user);
        return ResultBean.ok(usersVo);
    }

    @PostMapping("login")
    public ResultBean login(@RequestBody Users user) throws Exception {
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return ResultBean.errorMsg("用户名和密码不能为空");
        }
        Users queryUser = userService.queryUserForLogin(user.getUsername(), MD5Utils.getMD5Str(user.getPassword()));
        if (queryUser == null) {
            return ResultBean.errorMsg("用户名或密码错误");
        }
        queryUser.setPassword(null);
        UsersVo usersVo = setUserRedisVo(queryUser);
        return ResultBean.ok(usersVo);
    }

    @PostMapping("logout")
    public ResultBean logout(String userId) {
        if (StringUtils.isBlank(userId)) {
            return ResultBean.errorMsg("用户id不能为空");
        }
        redisTemplate.delete(USER_REDIS_SESSION + ":" + userId);
        return ResultBean.ok();
    }

    @PostMapping("updateFace")
    public ResultBean updateFace(String userId, MultipartFile file) {
        if (StringUtils.isBlank(userId) || file == null) {
            return ResultBean.errorMsg("参数错误");
        }
        String path = userService.updateFace(userId, file);
        return ResultBean.ok(path);
    }

    private UsersVo setUserRedisVo(Users user) {
        String userToken = UUID.randomUUID().toString();
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(user, usersVo);
        usersVo.setUserToken(userToken);
        redisTemplate.opsForValue().set(USER_REDIS_SESSION + ":" + user.getId(), userToken, 30, TimeUnit.MINUTES);
        return usersVo;
    }
}
