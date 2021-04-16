package com.xd.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xd.entity.*;
import com.xd.mapper.DeptMapper;
import com.xd.mapper.MeunMapper;
import com.xd.mapper.UserMapper;
import com.xd.redis.RedisUtil;
import com.xd.service.UserService;
import com.xd.utils.MD5key;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-07 13:49
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MeunMapper meunMapper;

    @Autowired
    private DeptMapper deptMapper;

    @Resource
    private RedisUtil redisUtil;

    public List<UserBean> getList() {
        return userMapper.selectByExample(null);
    }

    public List<UserBean> getUserListConn(UserBean userBean, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        UserBeanExample example = new UserBeanExample();
        UserBeanExample.Criteria criteria = example.createCriteria();
        if(userBean!=null){
            if(userBean.getUname()!=null&&userBean.getUname().length()>=1){
                criteria.andUnameLike("%"+userBean.getUname()+"%");
            }
            if(userBean.getAge()!=null&&userBean.getAge()>=1){
                criteria.andAgeGreaterThanOrEqualTo(userBean.getAge());
            }
        }
        List<UserBean> list = userMapper.selectByExample(example);
        PageInfo<UserBean> pageInfo = new PageInfo<UserBean>(list);
        return null;
    }

    public List<MeunBean> getMeunList(UserBean ub) {
        if(ub!=null){
            List<MeunBean> list = null;
            //从缓存中获取用户列表
            Object userListCache = redisUtil.getObject(ub.getId());

            //判断缓存中是否存在
            if (userListCache != null) {//不空，则强转返回
                System.out.println("redis中存在，直接返回");
                list = (List<MeunBean>) userListCache;

            }else{
                System.out.println("redis缓存中不存在，从数据库中取出，并且放入缓存");
                //查询数据库，取出
                list = userMapper.getUserMeunListById(ub.getId());
                //放入redis缓存
                redisUtil.putObject(ub.getId(), list);
            }
            return list;
        }
        return null;
    }

    public List<MeunBean> getMeunList2(UserBean ub) {
        if (ub!=null){
           List<MeunBean> list = userMapper.getUserMeunListById(ub.getId());
            return list;
        }
        return null;
    }

    public UserBean getUserVoById(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        Long[] deptids = userMapper.getUserDeptidsById(id);
        userBean.setDeptids(deptids);
        List<DeptBean> deptBeanList = deptMapper.selectByExample(null);
        userBean.setDlist(deptBeanList);
        return userBean;
    }

    public void saveUserDept(Long id, Long[] deptids) {
        userMapper.deleteUserdept(id);
        if (deptids!=null&&deptids.length>=1){
            for (Long deptid : deptids) {
                userMapper.insertUserDept(id,deptid);
            }
        }
    }

    public UserBean getUserInfo(Long id) {
        UserBean userBean = userMapper.selectByPrimaryKey(id);
        /*
        *开始去查询这个用户有没有部门
        * */
        List<DeptBean> dlist = userMapper.getUserDeptById(id);

        /**
         * 开始循环部门，查询部门里面的职位
         */
        if (dlist!=null&&dlist.size()>=1){
            for (DeptBean deptBean : dlist) {
                List<PostBean> plist = deptMapper.getDeptPostList(deptBean.getId());
                Long[] postids = deptMapper.getUserPostByIdAndDeptId(id,deptBean.getId());
                deptBean.setPostids(postids);
                deptBean.setPlist(plist);
            }
        }
        userBean.setDlist(dlist);
        return userBean;
    }

    public void saveUserPost(UserBean userBean) {
        //先删用户的职位

        if (userBean!=null){
            userMapper.deleteUserPost(userBean.getId());
            if (userBean.getDlist()!=null&&userBean.getDlist().size()>=1){
                for (DeptBean deptBean : userBean.getDlist()) {
                    if (deptBean.getPostids()!=null&&deptBean.getPostids().length>=1){
                        for (Long postid : deptBean.getPostids()) {
                            userMapper.saveUserPost(userBean.getId(),postid);
                        }
                    }
                }
            }
        }

    }

    public UserBean getLogin(UserBean ub) {
        if (ub!=null){
            List<UserBean> list = userMapper.getLogin(ub);
            if (list!=null&&list.size()==1){
                //到这里表示用用户名或者手机号查询到了
                //开始比较密码，对比密码之前需要加密密码
                //加密密码有很多，常用的有md5
                UserBean userBean = list.get(0);
                //页面用户输入的密码，进行加密和数据库里面的密码进行比较，相等正确
                String pwd = ub.getPwd();
                //也就是这里的加盐加密规则和注册的要一致，就好了。都是一个人负责的
                pwd = userBean.getPwdsalt()+pwd+userBean.getPwdsalt();
                MD5key md5key = new MD5key();
                String newpwd = md5key.getkeyBeanofStr(pwd);

                //相等就返回，其他都是空
                if (newpwd.equals(userBean.getPwd())){
                    return  userBean;
                }
            }
        }
        return null;
    }

    @Test
    public void test(){
        String pwd = "123456";
        pwd = "1234"+pwd+"1234";
        MD5key md5key = new MD5key();
        String newpwd = md5key.getkeyBeanofStr(pwd);
        System.out.println(newpwd);
    }


}
