package com.xd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xd.entity.DeptBean;
import com.xd.entity.DeptBeanExample;
import com.xd.entity.PostBean;
import com.xd.mapper.DeptMapper;
import com.xd.mapper.PostMapper;
import com.xd.service.DeptService;
import com.xd.utils.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 11:51
 */
@Service
public class DeptServiceImpl implements DeptService {
    
    @Resource
    private DeptMapper deptMapper;

    @Resource
    private PostMapper postMapper;
    
    public Page<DeptBean> getDeptListConn(DeptBean deptBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        DeptBeanExample example = new DeptBeanExample();
        DeptBeanExample.Criteria criteria = example.createCriteria();
        if (deptBean!=null){
            if(deptBean.getDeptname()!=null&&deptBean.getDeptname().length()>=1){
                criteria.andDeptnameLike("%"+deptBean.getDeptname()+"%");
            }
        }
        List<DeptBean> list = deptMapper.selectByExample(example);
        PageInfo<DeptBean> pageInfo = new PageInfo<DeptBean>(list);
        Long total = pageInfo.getTotal();
        Page<DeptBean> page = new Page<DeptBean>(pageInfo.getPageNum()+"",total.intValue(),pageInfo.getPageSize()+"");
        page.setList(list);
        return page;
    }


    public DeptBean getDeptByDeptId(Long id) {
        //查询人员对象
        DeptBean deptBean = deptMapper.selectByPrimaryKey(id);
        //查询全部的职位
        List<PostBean> postList = postMapper.selectByExample(null);
        deptBean.setPlist(postList);
        //查询已经选择的科室
        Long[] postIds = deptMapper.getDeptPostIds(id);
        deptBean.setPostids(postIds);
        return deptBean;
    }

    public void saveDeptPost(Long id, Long[] postids) {
        deptMapper.deleteDeptPostpid(id);
        if (postids!=null&&postids.length>=1){
            for (Long postid : postids) {
                deptMapper.seleteDeptPostid(id,postid);
            }
        }
    }
}
