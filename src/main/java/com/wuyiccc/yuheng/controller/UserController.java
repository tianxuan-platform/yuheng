package com.wuyiccc.yuheng.controller;

import com.wuyiccc.yuheng.infrastructure.pojo.PageVO;
import com.wuyiccc.yuheng.infrastructure.pojo.R;
import com.wuyiccc.yuheng.pojo.bo.*;
import com.wuyiccc.yuheng.pojo.vo.UserVO;
import com.wuyiccc.yuheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:21
 * 用户管理接口
 */
@Slf4j
@Validated
@RequestMapping("/user")
@RestController
public class UserController {

    @Resource
    private UserService userService;


    /**
     * 用户登录接口
     */
    @PostMapping("/login")
    public R<String> login(@Valid @RequestBody UserLoginBO userLoginBO) {

        String token = userService.login(userLoginBO);
        return R.data(token);
    }

    /**
     * 退出登录接口
     */
    @PostMapping("/logout")
    public R<String> logout() {

        return R.ok();
    }


    /**
     * 获取当前登录用户的信息
     */
    @GetMapping("/getCurrentUserInfo")
    public R<UserVO> getCurrentUserInfo() {

        UserVO userVO = userService.findCurrentUserVO();

        return R.ok(userVO);
    }


    /**
     * 新增用户
     */
    @PostMapping("/addUser")
    public R<String> addUser(@Valid @RequestBody UserCreateBO userCreateBO) {

        userService.addUser(userCreateBO);
        return R.ok();
    }

    /**
     * 删除用户
     */
    @PostMapping("/removeUser")
    public R<String> removeUser(@RequestParam("id") String id) {

        userService.removeUser(id);
        return R.ok();
    }


    /**
     * 根据id获取用户信息
     */
    @GetMapping("/getUserById")
    public R<UserVO> getUserById(@RequestParam("id") String id) {

        UserVO userVO = userService.findUserById(id);
        return R.ok(userVO);
    }


    /**
     * 更新用户信息
     */
    @PostMapping("/updateUser")
    public R<String> updateUser(@Valid @RequestBody UserUpdateBO userUpdateBO) {

        userService.updateUser(userUpdateBO);
        return R.ok();
    }

    /**
     * 分页查询用户信息
     */
    @PostMapping("/pageQueryUser")
    public R<PageVO<UserVO>> pageQueryUser(@Valid @RequestBody UserPageQueryBO userPageQueryBO) {

        PageVO<UserVO> pageResult = userService.pageQueryUser(userPageQueryBO);
        return R.ok(pageResult);
    }

    /**
     * 更新当前登录用户密码
     */
    @PostMapping("/updateMyPassword")
    public R<String> updateMyPassword(@Valid @RequestBody MyUserPasswordUpdateBO myUserPasswordUpdateBO) {

        userService.updateMyUserPassword(myUserPasswordUpdateBO);
        return R.ok();
    }

    /**
     * 更新指定户名密码
     */
    @PostMapping("/updateUserPassword")
    public R<String> updateUserPassword(@Valid @RequestBody UserPasswordUpdateBO userPasswordUpdateBO) {

        userService.updateUserPassword(userPasswordUpdateBO);
        return R.ok();
    }


}
