package com.xd.mapper;

import com.xd.entity.DeptBean;
import com.xd.entity.MeunBean;
import com.xd.entity.UserBean;
import com.xd.entity.UserBeanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    long countByExample(UserBeanExample example);

    int deleteByExample(UserBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(UserBean record);

    int insertSelective(UserBean record);

    List<UserBean> selectByExample(UserBeanExample example);

    UserBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByExample(@Param("record") UserBean record, @Param("example") UserBeanExample example);

    int updateByPrimaryKeySelective(UserBean record);

    int updateByPrimaryKey(UserBean record);

    Long[] getUserDeptidsById(@Param("id") Long id);


    void deleteUserdept(Long id);

    void insertUserDept(@Param("userid") Long userid,@Param("deptid") Long deptid);

    List<DeptBean> getUserDeptById(@Param("id") Long id);


    void saveUserPost(@Param("userid") Long userid, @Param("postid") Long postid);

    void deleteUserPost(@Param("id") Long id);


    List<UserBean> getLogin(UserBean ub);

    List<MeunBean> getUserMeunListById(Long id);
}