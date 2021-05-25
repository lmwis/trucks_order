package com.example.trucks_order.service;

import com.example.trucks_order.controller.viewobject.UserVO;
import com.example.trucks_order.dataobject.UserDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.service.model.UserModel;
import org.apache.catalina.User;

/**
 * 用户操作接口
 * @author lmwis on 2019-04-09 15:12
 */
public interface UserService {
    public UserVO register(UserModel userModel) throws BusinessException;

    public UserDO login(UserModel userModel) throws BusinessException;
}
