package com.nuc.meerkatx.springbootstudy.service.impl;

import com.nuc.meerkatx.springbootstudy.mapper.UserMapper;
import com.nuc.meerkatx.springbootstudy.pojo.User;
import com.nuc.meerkatx.springbootstudy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * @ClassName: UserServiceImpl
 * @Auther: MeerkatX
 * @Date: 2020-08-31 20:00
 * @Description:
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    private PlatformTransactionManager txManager;
    private TransactionDefinition txDefinition = new DefaultTransactionDefinition();

    private TransactionTemplate transactionTemplate;

    @Override
    @Transactional//声明式事务
    public User selectUserById(Integer id) {
        /*
        //编程式事务用法
        TransactionStatus txStatus = txManager.getTransaction(txDefinition);
        try {
            User user = userMapper.selectUserById(id);
            txManager.commit(txStatus);
        } catch (Exception e) {
            txManager.rollback(txStatus);
        }
        */

        /*
        //编程式事务template用法，相当于把编程式事务（上面的代码的模板代码抽出来）
        transactionTemplate.execute(new TransactionCallback() {

            @Override
            public Object doInTransaction(TransactionStatus status) {
                try {
                    User user = userMapper.selectUserById(id);
                } catch (Exception e) {
                    status.setRollbackOnly();
                }
            }
        });
        */

        return userMapper.selectUserById(id);
    }
}
