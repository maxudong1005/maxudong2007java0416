package com.xd.controller;

import com.xd.entity.MeunBean;
import com.xd.service.MeunService;
import com.xd.utils.ResultInfo;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 18:43
 */
@RequestMapping("meun")
@RestController
public class MeunController {

    @Resource
    private MeunService meunService;

    @RequestMapping("getMeunListByPid")
    public List<MeunBean> getMeunListByPid(@RequestParam(defaultValue = "1") Long pid){
       return meunService.getMeunListByPid(pid);
    }

    @RequestMapping("saveMeun")
    public ResultInfo saveMeun(@RequestBody MeunBean meunBean){
        try {
            meunService.saveMeun(meunBean);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }
    }

    @RequestMapping("deleteMeunById")
    public ResultInfo deleteMeunById(Long id){
        try {
            meunService.deleteMeunById(id);
            return new ResultInfo(true,"删除成功");
        }catch (Exception e){
            return new ResultInfo(false,"删除失败");
        }
    }


}
