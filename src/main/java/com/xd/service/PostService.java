package com.xd.service;

import com.xd.entity.MeunBean;
import com.xd.entity.PostBean;
import com.xd.utils.Page;

import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 16:38
 */
public interface PostService {
    Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunListById(Long id);

    void savePostMeun(Long postid, Long[] ids);
}
