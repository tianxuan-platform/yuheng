package com.wuyiccc.yuheng.infrastructure.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @author wuyiccc
 * @date 2023/9/12 23:41
 * <br>
 * 分页查询实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageParam {

    @NotNull(message = "当前页不可为null")
    @Min(value = 1, message = "current的值最小为1")
    private Integer current = 1;

    @NotNull(message = "size不能为null")
    @Min(value = 1, message = "size的值为1")
    private Integer size = 10;

}

