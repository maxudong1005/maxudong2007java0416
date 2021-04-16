package com.xd.controller;


import com.xd.entity.DeptBean;
import com.xd.service.DeptService;
import com.xd.utils.Page;


import com.xd.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 11:48
 */
@RequestMapping("dept")
@RestController
public class DeptController {

    @Resource
    private DeptService deptService;

    @RequestMapping("getDeptListConn")
    public Page<DeptBean> getDeptListConn(@RequestBody DeptBean deptBean, @RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        Page<DeptBean> deptListConn = deptService.getDeptListConn(deptBean, pageNum, pageSize);
        return deptListConn;
    }

    @RequestMapping("getDeptByDeptId")
    public DeptBean getDeptByDeptId(Long id){
        return deptService.getDeptByDeptId(id);
    }

    @RequestMapping("saveDeptPost")
    public ResultInfo saveDeptPost(Long id,@RequestBody Long[] postids){
        System.out.println(id);
        System.out.println(postids);
        try {
            deptService.saveDeptPost(id,postids);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }
    }








}
