package com.wuyiccc.yuheng.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageHelper;
import com.wuyiccc.yuheng.convert.UserConvert;
import com.wuyiccc.yuheng.infrastructure.code.YuhengBizCode;
import com.wuyiccc.yuheng.infrastructure.exception.CustomException;
import com.wuyiccc.yuheng.infrastructure.pojo.PageVO;
import com.wuyiccc.yuheng.infrastructure.utils.Md5Utils;
import com.wuyiccc.yuheng.infrastructure.utils.SecurityUtils;
import com.wuyiccc.yuheng.mapper.UserMapper;
import com.wuyiccc.yuheng.pojo.dto.*;
import com.wuyiccc.yuheng.pojo.entity.UserEntity;
import com.wuyiccc.yuheng.pojo.vo.UserVO;
import com.wuyiccc.yuheng.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:23
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String login(UserLoginDTO userLoginDTO) {

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserEntity::getUsername, userLoginDTO.getUsername());

        List<UserEntity> userEntityList = userMapper.selectList(wrapper);

        if (CollectionUtils.isEmpty(userEntityList)) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }
        UserEntity userEntity = userEntityList.get(0);
        String slat = userEntity.getSlat();
        String enPassword = Md5Utils.encrypt(userLoginDTO.getPassword(), slat);
        if (!Objects.equals(userEntity.getPassword(), enPassword)) {
            throw new CustomException(YuhengBizCode.USER_NAME_OR_PASSWORD_ERROR);
        }
        StpUtil.login(userEntity.getId());
        return StpUtil.getTokenValue();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addUser(UserCreateDTO userCreateDTO) {

        SecurityUtils.checkPermission();

        UserEntity userEntity = UserConvert.INSTANCE.convertToUserEntity(userCreateDTO);
        // 1. 生成slat
        String slat = Md5Utils.generateSlat();
        userEntity.setSlat(slat);
        // 2. password加密
        String enPassword = Md5Utils.encrypt(userCreateDTO.getPassword(), slat);
        userEntity.setPassword(enPassword);

        userMapper.insert(userEntity);
    }


    @Override
    public UserVO findCurrentUserVO() {

        String userId = SecurityUtils.getUserId();

        UserEntity userEntity = userMapper.selectById(userId);
        if (Objects.isNull(userEntity)) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }
        return UserConvert.INSTANCE.covertToUserVO(userEntity);
    }

    @Override
    public void removeUser(String id) {

        SecurityUtils.checkPermission();

        int res = userMapper.deleteById(id);
        if (res == 0) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }
    }

    @Override
    public UserVO findUserById(String id) {

        UserEntity userEntity = userMapper.selectById(id);
        if (Objects.isNull(userEntity)) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }

        return UserConvert.INSTANCE.covertToUserVO(userEntity);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUser(UserUpdateDTO userUpdateDTO) {

        SecurityUtils.checkPermission();

        UserEntity userEntity = UserConvert.INSTANCE.convertToUserEntity(userUpdateDTO);
        int res = userMapper.updateById(userEntity);
        if (res != 1) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }
    }

    @Override
    public PageVO<UserVO> pageQueryUser(UserPageQueryDTO userPageQueryDTO) {

        LambdaQueryWrapper<UserEntity> wrapper = new LambdaQueryWrapper<>();
        String username = userPageQueryDTO.getUsername();
        if (StringUtils.isNotBlank(username)) {
            wrapper.like(UserEntity::getUsername, username);
        }


        PageHelper.startPage(userPageQueryDTO.getCurrent(), userPageQueryDTO.getSize());
        List<UserEntity> userEntityList = userMapper.selectList(wrapper);

        PageVO<UserEntity> userEntityPage = PageVO.build(userEntityList);

        List<UserVO> userVOList = UserConvert.INSTANCE.convertToUserVOList(userEntityList);

        PageVO<UserVO> resPage = new PageVO<>();
        resPage.setCurrentPageNum(userEntityPage.getCurrentPageNum());
        resPage.setTotalPageNums(userEntityPage.getTotalPageNums());
        resPage.setTotalRecordNums(userEntityPage.getTotalRecordNums());
        resPage.setRecords(userVOList);
        return resPage;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateUserPassword(UserPasswordUpdateDTO userPasswordUpdateDTO) {

        SecurityUtils.checkPermission();

        doUpdateUserPassword(userPasswordUpdateDTO);
    }


    @Override
    public void updateMyUserPassword(MyUserPasswordUpdateDTO myUserPasswordUpdateDTO) {

        UserPasswordUpdateDTO dto = new UserPasswordUpdateDTO(SecurityUtils.getUserName(), myUserPasswordUpdateDTO.getPassword());
        doUpdateUserPassword(dto);
    }

    @Override
    public UserEntity findUserEntityById(String id) {

        return userMapper.selectById(id);
    }


    private void doUpdateUserPassword(UserPasswordUpdateDTO userPasswordUpdateDTO) {
        UserEntity eldUserEntity = userMapper.selectById(userPasswordUpdateDTO.getId());
        if (Objects.isNull(eldUserEntity)) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }


        // 2. password加密
        String enPassword = Md5Utils.encrypt(userPasswordUpdateDTO.getPassword(), eldUserEntity.getSlat());
        UserEntity newUser = new UserEntity();
        newUser.setId(userPasswordUpdateDTO.getId());
        newUser.setPassword(enPassword);
        newUser.setVersion(eldUserEntity.getVersion());

        int res = userMapper.updateById(newUser);
        if (res != 1) {
            throw new CustomException(YuhengBizCode.USER_NOT_FOUND);
        }
    }

}
