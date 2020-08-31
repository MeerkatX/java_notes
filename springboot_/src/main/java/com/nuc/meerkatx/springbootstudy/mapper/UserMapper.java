package com.nuc.meerkatx.springbootstudy.mapper;

import com.nuc.meerkatx.springbootstudy.pojo.User;

/**
 * @ClassName: UserMapper
 * @Auther: MeerkatX
 * @Date: 2020-08-31 19:55
 * @Description:
 */
public interface UserMapper {

    User selectUserById(Integer id);
}
