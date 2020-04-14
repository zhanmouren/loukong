package com.koron.inwlms.bean.DTO.common;

import io.swagger.annotations.ApiModelProperty;

/**
 * 分页bean
 *
 * @Author csh
 * @Date 2020.03.09
 */
public class BaseDTO {
	
  
	/**
     * 页数
     */
    @ApiModelProperty(value = "页数")
    private Integer page = 1;

    /**
     * 每页记录数
     */
    @ApiModelProperty(value = "每页记录数")
    private Integer pageCount = 20;
    
    /**
     * 排序（字段+空格+升序/降序，eg:TIME DESC/ASC,其中，DESC是降序，ASC是升序）
     */
    @ApiModelProperty(value = "排序（字段+空格+升序/降序，eg:TIME DESC/ASC,其中，DESC是降序，ASC是升序）")
    private String orderby;

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
     * 获取排序
     */
	public String getOrderby() {
		return orderby;
	}

	/**
     * 设置排序
     */
	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}
}

