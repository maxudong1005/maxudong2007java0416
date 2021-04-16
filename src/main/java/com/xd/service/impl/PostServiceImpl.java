package com.xd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xd.entity.MeunBean;
import com.xd.entity.PostBean;
import com.xd.entity.PostBeanExample;
import com.xd.mapper.MeunMapper;
import com.xd.mapper.PostMapper;
import com.xd.service.PostService;
import com.xd.utils.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 16:39
 */
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostMapper postMapper;

    @Autowired
    private MeunMapper meunMapper;


    public Page<PostBean> getPostListConn(PostBean postBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        PostBeanExample example = new PostBeanExample();
        PostBeanExample.Criteria criteria = example.createCriteria();
        if(postBean!=null){
            if(postBean.getPostname()!=null&&postBean.getPostname().length()>=1){
                criteria.andPostnameLike("%"+postBean.getPostname()+"%");
            }
        }
        List<PostBean> list = postMapper.selectByExample(example);
        PageInfo<PostBean> pageInfo = new PageInfo<PostBean>(list);
        Long total = pageInfo.getTotal();
        Page<PostBean> page = new Page<PostBean>(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }

    public List<MeunBean> getMeunListById(Long postid) {
        //全查菜单
        List<MeunBean> list = meunMapper.selectByExample(null);

        List<Long> meunids = postMapper.getPostMeunIds(postid);
        //要是原来有菜单的，需要回显，ztree回显，在后台直接回显就OK；
        if (meunids!=null&&meunids.size()>=1){
            for (Long meunid : meunids) {
                for (MeunBean bean : list) {
                    if(meunid.equals(bean.getId())){
                        bean.setChecked(true);
                        break;
                    }
                }
            }
        }
        return list;
    }

    public void savePostMeun(Long postid, Long[] ids) {
        //先删除
        postMapper.deletePostMeunByPostid(postid);
        //新增
        if (ids!=null&&ids.length>=1){
            for (Long meunid : ids) {
                postMapper.savePostMeun(postid,meunid);
            }
        }
    }
}
