package com.wuyiccc.yuheng.pojo.bo;

import com.wuyiccc.yuheng.infrastructure.pojo.PageParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wuyiccc
 * @date 2023/9/15 23:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserPageQueryBO extends PageParam {

    /**
     * 用户名
     */
    private String username;
}
