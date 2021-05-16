package com.lb.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lb.entity.User;

/**
 * @author luobo
 * @date 2021-04-04 15:09
 * @description
 */
public interface PersonInfoService extends IService<User> {
    /**
     * 修改个人信息操作
     * */
    User editPersonInformation(Object username);

    /**
     * 修改密码
     * */
    int updatePassword(User user);

}
