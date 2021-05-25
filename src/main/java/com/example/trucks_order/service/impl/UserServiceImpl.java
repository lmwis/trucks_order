package com.example.trucks_order.service.impl;

import com.example.trucks_order.Validator.ValidationResult;
import com.example.trucks_order.Validator.ValidatorImpl;
import com.example.trucks_order.controller.viewobject.UserVO;
import com.example.trucks_order.dataobject.UserDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.mapper.UserDOMapper;
import com.example.trucks_order.service.UserService;
import com.example.trucks_order.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author lmwis on 2019-04-09 15:14
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDOMapper userDOMapper;
    @Autowired
    ValidatorImpl validator;
    @Override
    public UserVO register(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //校验参数是否合法
        ValidationResult results = validator.validate(userModel);
        if(results!=null && results.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,results.getErrMsg());
        }
        //查询邮箱是否已经注册
        UserDO userDO1 = userDOMapper.selectByEmail(userModel.getEmail());
        if(userDO1!=null){
            throw  new BusinessException(EmBusinessError.USER_NOT_EXIST,"该邮箱已经注册");
        }
        //写入数据库
        UserDO userDO = convertDOFromModel(userModel);
        userDOMapper.insertSelective(userDO);

        return converVOFromModel(userModel);
    }
    private UserDO convertDOFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserDO userDO = new UserDO();
        BeanUtils.copyProperties(userModel,userDO);

        return userDO;
    }
    private UserVO converVOFromModel(UserModel userModel){
        if(userModel==null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel,userVO);
        return userVO;
    }

    public UserDO login(UserModel userModel) throws BusinessException {
        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //校验参数是否合法
        ValidationResult results = validator.validate(userModel);
        if(results!=null && results.isHasErrors()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,results.getErrMsg());
        }

        //判断登录有效性
        UserDO userDO = userDOMapper.selectByEmail(userModel.getEmail());
        if (userDO == null) {
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
        }
        if (userDO.getPassword().equals(userModel.getPassword())) {
            return userDO;
        } else {
            return null;
        }

    }
}
