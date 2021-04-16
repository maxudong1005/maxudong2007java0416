package com.xd.controller;

import com.xd.entity.MeunBean;
import com.xd.entity.PostBean;
import com.xd.service.PostService;
import com.xd.utils.Page;
import com.xd.utils.ResultInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 16:37
 */
@RequestMapping("post")
@RestController
public class PostController {

    @Resource
    private PostService postService;

    @RequestMapping("getPostListConn")
    public Page<PostBean> getPostListConn(@RequestBody PostBean postBean, @RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "3") Integer pageSize){

        Page<PostBean> postListConn = postService.getPostListConn(postBean, pageNum, pageSize);
        System.out.println(postListConn);
        return postListConn;
    }

    @RequestMapping("getMeunListById")
    public List<MeunBean> getMeunListById(Long id){
        return postService.getMeunListById(id);
    }

    @RequestMapping("savePostMeun")
    public ResultInfo savePostMeun(Long postid,@RequestBody Long[] ids){
        try {
            postService.savePostMeun(postid,ids);
            return new ResultInfo(true,"保存成功");
        }catch (Exception e){
            return new ResultInfo(false,"保存失败");
        }
    }


}
