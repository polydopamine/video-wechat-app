package com.video.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.video.entity.Users;
import com.video.exception.MinioException;
import com.video.mapper.UsersMapper;
import com.video.service.IUserService;
import com.video.service.MinioService;
import com.video.utils.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author wangsihang
 * @date 2021/3/23 下午10:16
 */
@Service
@Slf4j
public class UserServiceImpl implements IUserService {
    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    @Autowired
    private MinioService minioService;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users existUser = usersMapper.selectOne(new QueryWrapper<Users>().lambda().eq(Users::getUsername, username));
        return existUser != null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users user) {
        String userId = sid.nextShort();
        user.setId(userId);
        usersMapper.insert(user);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String password) {
        Users user = usersMapper.selectOne(new QueryWrapper<Users>().lambda().eq(Users::getUsername, username).eq(Users::getPassword, password));
        return user;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String updateFace(String userId, MultipartFile file) {
        String path = "face/" + userId + "/" + file.getOriginalFilename();
        try {
            minioService.upload(path, file.getInputStream(), file.getContentType());
        } catch (MinioException e) {
            e.printStackTrace();
            log.error("图片上传异常, [{}]", "id = " + userId , e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("图片解析异常, [{}]", "id = " + userId , e);
        }
        Users updateUser = new Users();
        updateUser.setId(userId);
        updateUser.setFaceImage(path);
        usersMapper.updateById(updateUser);
        return path;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserInfo(String userId) {
        Users users = usersMapper.selectById(userId);
        return users;
    }
}
