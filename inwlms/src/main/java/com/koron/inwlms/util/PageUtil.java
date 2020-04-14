package com.koron.inwlms.util;

import com.koron.inwlms.bean.VO.common.PageVO;

/**
 * 深圳科荣软件有限公司
 *
 * @ClassName PageUtil
 * @Description
 * @Author hongxl
 * @Date 2019/11/11 16:20
 */
public class PageUtil {

    /**
     * 获取列表结果分页信息
     *
     * @param page
     * @param pageCount
     * @param size
     * @return
     */
    public static PageVO getPageBean(int page, int pageCount, int size) {
        PageVO result = new PageVO();
        result.setPage(page);
        result.setPageCount(pageCount);
        result.setRowNumber(size);
        result.setTotalPage((int) Math.ceil(Double.valueOf(size / pageCount))+1);
        return result;
    }
}
