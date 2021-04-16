package com.xd.service;

import com.xd.entity.DeptBean;
import com.xd.utils.Page;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 11:50
 */
public interface DeptService {
    Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize);

    DeptBean getDeptByDeptId(Long id);

    void saveDeptPost(Long id, Long[] postids);
}
