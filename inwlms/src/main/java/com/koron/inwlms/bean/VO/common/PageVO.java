package com.koron.inwlms.bean.VO.common;

/**
 * 深圳科荣软件有限公司
 *
 * @ClassName PageVO
 * @Description
 * @Author hongxl
 * @Date 2019/11/11 16:21
 */
public class PageVO {

    /**
     * 页数
     */
    private Integer page;

    /**
     * 每页记录数
     */
    private Integer pageCount;

    /**
     * 总行数
     */
    private Integer rowNumber;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 获取 页数
     */
    public Integer getPage() {
        return this.page;
    }

    /**
     * 设置 页数
     */
    public void setPage(Integer page) {
        this.page = page;
    }

    /**
     * 获取 每页记录数
     */
    public Integer getPageCount() {
        return this.pageCount;
    }

    /**
     * 设置 每页记录数
     */
    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    /**
     * 获取 总行数
     */
    public Integer getRowNumber() {
        return this.rowNumber;
    }

    /**
     * 设置 总行数
     */
    public void setRowNumber(Integer rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * 获取 总页数
     */
    public Integer getTotalPage() {
        return this.totalPage;
    }

    /**
     * 设置 总页数
     */
    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
