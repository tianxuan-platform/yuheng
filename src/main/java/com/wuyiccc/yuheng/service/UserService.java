package com.wuyiccc.yuheng.service;

import com.wuyiccc.yuheng.infrastructure.pojo.PageVO;
import com.wuyiccc.yuheng.pojo.bo.*;
import com.wuyiccc.yuheng.pojo.entity.UserEntity;
import com.wuyiccc.yuheng.pojo.vo.UserVO;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:22
 */
public interface UserService {


    /**
     * 用户登录
     *
     * @param userLoginBO 用户登录参数
     * @return 用户登录token
     */
    String login(UserLoginBO userLoginBO);

    /**
     * 新建用户
     *
     * @param userCreateBO 新建用户实体
     */
    void addUser(UserCreateBO userCreateBO);


    /**
     * 查询当前登录的用户的信息
     *
     * @return 用户信息
     */
    UserVO findCurrentUserVO();


    /**
     * 删除指定id的用户
     *
     * @param id 用户id
     */
    void removeUser(String id);

    /**
     * 查询指定用户id的用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    UserVO findUserById(String id);


    /**
     * 更新指定用户信息
     *
     * @param userUpdateBO 用户更新信息
     */
    void updateUser(UserUpdateBO userUpdateBO);

    /**
     * 分页查询用户信息
     *
     * @param userPageQueryBO 分页查询参数
     * @return 分页结果
     */
    PageVO<UserVO> pageQueryUser(UserPageQueryBO userPageQueryBO);


    /**
     * 更新用户的密码
     *
     * @param userPasswordUpdateBO 更新之后的密码
     */
    void updateUserPassword(UserPasswordUpdateBO userPasswordUpdateBO);

    /**
     * 更新用户自己的密码
     *
     * @param myUserPasswordUpdateBO 用户自己的密码
     */
    void updateMyUserPassword(MyUserPasswordUpdateBO myUserPasswordUpdateBO);

    /**
     * 查询用户实体
     */
    UserEntity findUserEntityById(String id);

    /**
     * 批量删除用户
     * @param userList 需要删除的用户id列表
     */
    void removeUserList(List<String> userList);
}
