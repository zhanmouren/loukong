package com.koron.inwlms.bean.VO.common;


import com.github.pagehelper.Page;

import java.util.List;

/**
 * 深圳科荣软件有限公司
 * 分页VO
 * @ClassName PageVO
 * @Description
 * @Author hongxl
 * @Date 2019/8/2 16:28
 */
public class PageBean<T> extends BaseQueryBean{
    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6477868212171605239L;

    /**
     * 结果集
     */
    private List<T> dataList;

    /**
     * 构造函数
     */
    public PageBean() {
    }

    /**
     * 构造函数
     * @param page
     */
    public PageBean(Page<T> page) {
        init(page);
    }

    /**
     * 初始化
     * @param page
     */
    private void init(Page<T> page) {
        this.dataList = page.getResult();
        super.setPage(page.getPageNum());
        super.setPageCount(page.getPageSize());
        super.setTotalPage(page.getPages());
        super.setRowNumber(Integer.parseInt(String.valueOf(page.getTotal())));
    }


    /**
     * 获取 结果集
     */
    public List<T> getDataList() {
        return this.dataList;
    }

    /**
     * 设置 结果集
     */
    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
