package com.example.trucks_order.service.impl;

import com.example.trucks_order.dataobject.ConnecDO;
import com.example.trucks_order.dataobject.OrderDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.mapper.OrderDOMapper;
import com.example.trucks_order.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lmwis on 2019-04-28 14:56
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderDOMapper orderDOMapper;

    @Override
    public OrderDO saveOrder(OrderDO orderDO) throws BusinessException {
        if(orderDO==null){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        int i=orderDOMapper.insertSelective(orderDO);
        System.out.println(i);
        return orderDO;
    }

    @Override
    public List<OrderDO> getAllOrder() {

        List<OrderDO> orderDOList = orderDOMapper.selectAll();

        return orderDOList;
    }

    @Override
    public List<OrderDO> getByDriverId(Integer driverId) {

        List<ConnecDO> connecDOs = orderDOMapper.selectByDriverId(driverId);
        List<OrderDO> orders = new ArrayList<>();
        if(connecDOs.size()<=0){
            return  null;
        }
        for(ConnecDO c:connecDOs){
            if(c==null){
                continue;
            }
            OrderDO orderDO = orderDOMapper.selectByPrimaryKey(c.getOrderId());
            orders.add(orderDO);
        }
        return orders;
    }

    @Override
    public void changeOrderStatus2Accept(Integer orderId) throws BusinessException {

        //此处应该设置同步锁保证订单状态的一致性
        //高并发情况下，会存在将数据获取到系统中之后数据本身又被改变的情况
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        if(orderDO==null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"订单不存在");
        }
        if(orderDO.getStatus()==1){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"订单已经被接走");
        }
        changeStatus(orderDO,1);
    }

    @Override
    public void changeOrderStatus2Finish(Integer orderId) throws BusinessException {
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        if(orderDO==null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"订单不存在");
        }
        if(orderDO.getStatus()==2){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"订单已经被完成");
        }
        changeStatus(orderDO,2);
    }

    @Override
    public void changeOrderStatus2Pre(Integer orderId) throws BusinessException {
        OrderDO orderDO = orderDOMapper.selectByPrimaryKey(orderId);
        if(orderDO==null){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"订单不存在");
        }
        if(orderDO.getStatus()==0){
            throw new BusinessException(EmBusinessError.UNKNOWN_ERROR,"订单已经是未接状态");
        }
        changeStatus(orderDO,0);
    }

    private void changeStatus(OrderDO orderDO,Integer code){

        orderDO.setStatus(code);

        orderDOMapper.updateByPrimaryKeySelective(orderDO);
    }
}
