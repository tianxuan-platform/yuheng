package com.wuyiccc.yuheng.convert;

import com.wuyiccc.yuheng.pojo.dto.UserCreateDTO;
import com.wuyiccc.yuheng.pojo.dto.UserUpdateDTO;
import com.wuyiccc.yuheng.pojo.entity.UserEntity;
import com.wuyiccc.yuheng.pojo.vo.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2024/1/18 00:01
 */
@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);

    UserEntity convertToUserEntity(UserCreateDTO userCreateDTO);

    UserEntity convertToUserEntity(UserUpdateDTO userUpdateDTO);

    UserVO covertToUserVO(UserEntity userEntity);

    List<UserVO> convertToUserVOList(List<UserEntity> resList);
}
