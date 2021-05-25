package com.example.trucks_order.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.example.trucks_order.dataobject.ConnecDO;
import com.example.trucks_order.dataobject.DriverDO;
import com.example.trucks_order.dataobject.OrderDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.mapper.DriverDOMapper;
import com.example.trucks_order.mapper.OrderDOMapper;
import com.example.trucks_order.service.DriverService;
import com.example.trucks_order.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lmwis on 2019-04-28 15:36
 */
@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    DriverDOMapper driverDOMapper;

    @Autowired
    OrderDOMapper orderDOMapper;

    @Override
    public DriverDO register(DriverDO driverDO) throws BusinessException {

        if(driverDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        DriverDO driverDO2 = driverDOMapper.selectByName(driverDO.getDriverName());
        if(driverDO2!=null){
            throw  new BusinessException(EmBusinessError.USER_NOT_EXIST,"该用户已经注册");
        }

        Integer i =driverDOMapper.insertSelective(driverDO);

        //保障数据一致性再次查询
        DriverDO driverDO1 = driverDOMapper.selectByPrimaryKey(i);

        System.out.println(i);

        return driverDO1;
    }

    @Override
    public DriverDO login(UserModel userModel) throws BusinessException {

        if(userModel==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //根据输入的司机名查询
        DriverDO driverDO = driverDOMapper.selectByName(userModel.getEmail());
        if(driverDO==null || !StringUtils.equals(userModel.getPassword(),driverDO.getPassword())){
            throw new BusinessException(EmBusinessError.USER_NOT_EXIST,"该司机不存在或司机名密码错误");
        }

        return driverDO;
    }

    @Autowired
    OrderServiceImpl orderService;
    @Override
    public DriverDO addOrder(ConnecDO connecDO) throws BusinessException {

//        List<OrderDO> connecDOs = orderService.getByDriverId(connecDO.getDriverId());

        //添加进关联
        driverDOMapper.insertConnect(connecDO);
        //改变订单状态-->已接
        orderService.changeOrderStatus2Accept(connecDO.getOrderId());

        return null;
    }

    @Override
    public void finishOrder(Integer orderId) throws BusinessException {
        //改变订单状态-->已完成
        orderService.changeOrderStatus2Finish(orderId);
    }

    @Override
    public void cancelOrder(Integer driverId,Integer orderId) throws BusinessException {

        List<ConnecDO> connecDOS = orderDOMapper.selectByDriverId(driverId);
        ConnecDO connecDO = new ConnecDO();
        for(ConnecDO c :connecDOS){
            if(c.getOrderId()==orderId){
                connecDO = c;
            }
        }
        //删除关联
        driverDOMapper.delConnect(connecDO);
        //改变订单状态-->未接
        orderService.changeOrderStatus2Pre(orderId);
    }
}
