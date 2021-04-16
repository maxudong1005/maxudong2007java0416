package com.xd.mapper;

import com.xd.entity.MeunBean;
import com.xd.entity.MeunBeanExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MeunMapper {
    long countByExample(MeunBeanExample example);

    int deleteByExample(MeunBeanExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MeunBean record);

    int insertSelective(MeunBean record);

    List<MeunBean> selectByExample(MeunBeanExample example);

    MeunBean selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MeunBean record, @Param("example") MeunBeanExample example);

    int updateByExample(@Param("record") MeunBean record, @Param("example") MeunBeanExample example);

    int updateByPrimaryKeySelective(MeunBean record);

    int updateByPrimaryKey(MeunBean record);
}