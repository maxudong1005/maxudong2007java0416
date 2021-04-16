package com.xd.service;

import com.xd.entity.MeunBean;
import com.xd.entity.UserBean;

import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-07 13:48
 */
public interface UserService {
    List<UserBean> getList();

    List<UserBean> getUserListConn(UserBean userBean, Integer pageNum, Integer pageSize);

    List<MeunBean> getMeunList2(UserBean ub);

    UserBean getUserVoById(Long id);

    void saveUserDept(Long id, Long[] deptids);

    UserBean getUserInfo(Long id);

    void saveUserPost(UserBean userBean);

    UserBean getLogin(UserBean ub);
}
