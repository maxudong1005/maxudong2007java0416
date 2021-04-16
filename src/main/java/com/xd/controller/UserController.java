package com.xd.controller;


import com.xd.entity.MeunBean;
import com.xd.entity.UserBean;
import com.xd.service.UserService;
import com.xd.utils.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @version 1.0
 * @author: 马旭东
 * @date: 2021-04-07 13:46
 */

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;


    @RequestMapping("getLogin")
    public ResultInfo getLogin(@RequestBody UserBean ub, HttpServletRequest request){
       UserBean userBean =  userService.getLogin(ub);
       if (userBean==null){
           return new ResultInfo(false,"登录失败，用户或者密码错误！");
       }else{
            request.getSession().setAttribute("ub", userBean);
           return new ResultInfo(true,"登录成功");
       }
    }

    @RequestMapping("getMeunList")
    public List<MeunBean> getMeunList(HttpServletRequest request){
        /*
        * 在这里需要知道是谁登录的
        * 还要查询出不是按钮的菜单
        * */
        UserBean ub = (UserBean)request.getSession().getAttribute("ub");
        return userService.getMeunList2(ub);
    }

    @RequestMapping("getList")
    public List<UserBean> getList(){
        List<UserBean> list = userService.getList();
        System.out.println(list);
        return list;
    }

    @RequestMapping("test")
    public List<UserBean> getUserListConn(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "3") Integer pageSize){
        UserBean userBean = new UserBean();
        userBean.setAge(12);
        userBean.setUname("aa");
        return userService.getUserListConn(userBean,pageNum,pageSize);
    }

    /**
     * 去给用户选择部门
     */

    @RequestMapping("getUserVoById")
    public UserBean getUserVoById(Long id){
        return userService.getUserVoById(id);
    }

    @RequestMapping("saveUserDept")
    public ResultInfo saveUserDept(Long id,@RequestBody Long[] deptids){
        try {
            userService.saveUserDept(id,deptids);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
            return new ResultInfo(false,"编辑失败");
        }
    }

    @RequestMapping("getUserInfo")
    public UserBean getUserInfo(Long id){
        UserBean userInfo = userService.getUserInfo(id);
        return userInfo;
    }

    @RequestMapping("saveUserPost")
    public ResultInfo saveUserPost(@RequestBody UserBean userBean){
        try {
            userService.saveUserPost(userBean);
            return new ResultInfo(true,"编辑成功");
        }catch (Exception e){
           return new ResultInfo(false,"编辑失败");
        }
    }

    

}
