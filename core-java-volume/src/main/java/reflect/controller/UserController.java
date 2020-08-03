package reflect.controller;

import annotation.MyAnnotation;
import lombok.Data;
import reflect.service.UserService;

/**
 * @Auther: 徐少伟
 * @Date: 2020/7/3
 * @Description:
 */
@Data
public class UserController {

    @MyAnnotation //自己写的注解 类似Autowired 这里只起标记作用 配合Main2
    private UserService userService;
}
