package com.nuc.meerkatx.springbootstudy.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/10
 * @Description:
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    private String name;

    private int age;
}
