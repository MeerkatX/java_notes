package com.nuc.meerkatx.springbootstudy.service;

import com.nuc.meerkatx.springbootstudy.pojo.User;

/**
 * @ClassName: UserService
 * @Auther: MeerkatX
 * @Date: 2020-08-31 20:00
 * @Description:
 */
public interface UserService {
    public User selectUserById(Integer id);
}
