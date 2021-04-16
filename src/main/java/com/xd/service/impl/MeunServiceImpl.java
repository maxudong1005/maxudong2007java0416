package com.xd.service.impl;


import com.xd.entity.MeunBean;
import com.xd.entity.MeunBeanExample;
import com.xd.mapper.MeunMapper;
import com.xd.service.MeunService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 18:45
 */
@Service
public class MeunServiceImpl implements MeunService {

    @Resource
    private MeunMapper meunMapper;

    public List<MeunBean> getMeunListByPid(Long pid) {
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);
        return list;
    }

    public void saveMeun(MeunBean meunBean) {
        if(meunBean!=null){
            if(meunBean.getId()!=null){
                //修改
                meunMapper.updateByPrimaryKeySelective(meunBean);
            }else{
                meunMapper.insertSelective(meunBean);
            }

        }
    }

    List<Long> ids = new ArrayList<Long>();

    public void deleteMeunById(Long id) {
        getMeunListByPidToDelete(id);

        //在这里拿到ids，然后去删除
        for (Long id1 : ids) {
            meunMapper.deleteByPrimaryKey(id1);
        }
    }

    private void getMeunListByPidToDelete(Long pid) {
        ids.add(pid);
        MeunBeanExample example = new MeunBeanExample();
        MeunBeanExample.Criteria criteria = example.createCriteria();
        criteria.andPidEqualTo(pid);
        List<MeunBean> list = meunMapper.selectByExample(example);
       //满足就执行，不满足就结束
        if(list!=null&&list.size()>=1){
            for (MeunBean bean : list) {
                getMeunListByPidToDelete(bean.getId());
            }
        }
    }

}
