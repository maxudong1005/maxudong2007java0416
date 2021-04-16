package com.xd.mapper;

import com.xd.entity.DeptBean;
import com.xd.entity.DeptBeanExample;
import com.xd.entity.PostBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DeptMapper {
    long countByExample(DeptBeanExample example);

    int deleteByExample(DeptBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(DeptBean record);

    int insertSelective(DeptBean record);

    List<DeptBean> selectByExample(DeptBeanExample example);

    DeptBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") DeptBean record, @Param("example") DeptBeanExample example);

    int updateByExample(@Param("record") DeptBean record, @Param("example") DeptBeanExample example);

    int updateByPrimaryKeySelective(DeptBean record);

    int updateByPrimaryKey(DeptBean record);

     Long[] getDeptPostIds(Long deptid);

    void deleteDeptPostpid(Long id);

    void seleteDeptPostid(@Param("deptid") Long deptid,@Param("postid") Long postid);

    List<PostBean> getDeptPostList(@Param("deptid") Long deptid);

    Long[] getUserPostByIdAndDeptId(@Param("userid") Long userid,@Param("deptid") Long deptid);
}