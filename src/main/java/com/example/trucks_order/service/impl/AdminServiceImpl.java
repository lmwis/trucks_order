package com.example.trucks_order.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.trucks_order.dataobject.DriverDO;
import com.example.trucks_order.dataobject.OrderDO;
import com.example.trucks_order.dataobject.UserDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.mapper.DriverDOMapper;
import com.example.trucks_order.mapper.OrderDOMapper;
import com.example.trucks_order.mapper.UserDOMapper;
import com.example.trucks_order.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Driver;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lmwis on 2019-05-04 12:30
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    OrderDOMapper orderDOMapper;

    @Override
    public boolean login(String username, String password) throws BusinessException {
        if(StringUtils.equals(username,"admin") && StringUtils.equals(password,"admin")){
            return true;
        }
        return false;
    }

    @Override
    public void saveOrders(List<OrderDO> orderDOList) {
        for (OrderDO o:orderDOList){
            orderDOMapper.updateByPrimaryKeySelective(o);
        }
    }

    @Override
    public void delOrder(Integer orderId) {
        orderDOMapper.deleteByPrimaryKey(orderId);
    }

    @Autowired
    UserDOMapper userDOMapper;

    @Override
    public List<UserDO> getAllUser() {
        return userDOMapper.selectAll();
    }

    @Override
    public void delUser(Integer userId) {
        userDOMapper.deleteByPrimaryKey(userId);
    }

    @Override
    public void saveUsers(List<UserDO> userDOList) {
        for(UserDO u:userDOList){
            userDOMapper.updateByPrimaryKey(u);
        }
    }

    @Autowired
    DriverDOMapper driverDOMapper;
    @Override
    public List<DriverDO> getAllDriver() {
        return driverDOMapper.selectAll();
    }

    @Override
    public void delDriver(Integer driverId) {
        driverDOMapper.deleteByPrimaryKey(driverId);
    }

    @Override
    public void saveDriver(List<DriverDO> driverDOList) {
        for(DriverDO d: driverDOList){
            driverDOMapper.updateByPrimaryKeyOnlyInfo(d);
        }
    }
}
