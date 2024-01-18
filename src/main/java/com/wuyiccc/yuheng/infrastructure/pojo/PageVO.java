package com.wuyiccc.yuheng.infrastructure.pojo;

import com.github.pagehelper.PageInfo;
import lombok.Data;

import java.util.List;

/**
 * @author wuyiccc
 * @date 2023/12/10 15:00
 * <br>
 * 分页对象
 */
@Data
public class PageVO<T> {

    /**
     * 当前页码
     */
    private int currentPageNum;

    /**
     * 总页数
     */
    private long totalPageNums;

    /**
     * 总记录数
     */
    private long totalRecordNums;

    /**
     * 当前页记录数据
     */
    private List<T> records;



    public static <T> PageVO<T> build(List<T> list) {

        PageInfo<T> pageList = new PageInfo<>(list);
        PageVO<T> pageVO = new PageVO<>();
        pageVO.setRecords(pageList.getList());
        pageVO.setCurrentPageNum(pageList.getPageNum());
        pageVO.setTotalRecordNums(pageList.getTotal());
        pageVO.setTotalPageNums(pageList.getPages());
        return pageVO;
    }


}
