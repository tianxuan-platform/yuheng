package com.wuyiccc.yuheng.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.wuyiccc.yuheng.infrastructure.pojo.PageVO;
import com.wuyiccc.yuheng.infrastructure.pojo.R;
import com.wuyiccc.yuheng.pojo.dto.*;
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
    public R<String> login(@Valid @RequestBody UserLoginDTO userLoginDTO) {

        String token = userService.login(userLoginDTO);
        return R.data(token);
    }

    /**
     * 退出登录接口
     */
    @PostMapping("/logout")
    public R<String> logout() {

        StpUtil.logout();
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


    @PostMapping("/addUser")
    public R<String> addUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {

        userService.addUser(userCreateDTO);
        return R.ok();
    }

    @PostMapping("/removeUser")
    public R<String> removeUser(@RequestParam("id") String id) {

        userService.removeUser(id);
        return R.ok();
    }


    @GetMapping("/getUserById")
    public R<UserVO> getUserById(@RequestParam("id") String id) {

        UserVO userVO = userService.findUserById(id);
        return R.ok(userVO);
    }


    @PostMapping("/updateUser")
    public R<String> updateUser(@Valid @RequestBody UserUpdateDTO userUpdateDTO) {

        userService.updateUser(userUpdateDTO);
        return R.ok();
    }

    @PostMapping("/pageQueryUser")
    public R<PageVO<UserVO>> pageQueryUser(@Valid @RequestBody UserPageQueryDTO userPageQueryDTO) {

        PageVO<UserVO> pageResult = userService.pageQueryUser(userPageQueryDTO);
        return R.ok(pageResult);
    }

    @PostMapping("/updateMyPassword")
    public R<String> updateMyPassword(@Valid @RequestBody MyUserPasswordUpdateDTO myUserPasswordUpdateDTO) {

        userService.updateMyUserPassword(myUserPasswordUpdateDTO);
        return R.ok();
    }

    @PostMapping("/updateUserPassword")
    public R<String> updateUserPassword(@Valid @RequestBody UserPasswordUpdateDTO userPasswordUpdateDTO) {

        userService.updateUserPassword(userPasswordUpdateDTO);
        return R.ok();
    }


}
