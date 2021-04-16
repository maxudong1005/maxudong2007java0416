package com.xd.service;

import com.xd.entity.MeunBean;

import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-08 18:45
 */
public interface MeunService {
    List<MeunBean> getMeunListByPid(Long pid);

    void saveMeun(MeunBean meunBean);

    void deleteMeunById(Long id);
}
