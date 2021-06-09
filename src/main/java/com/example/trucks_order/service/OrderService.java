package com.example.trucks_order.service;

import com.example.trucks_order.dataobject.OrderDO;
import com.example.trucks_order.error.BusinessException;
import org.hibernate.criterion.Order;

import java.util.List;

/**
 * @author lmwis on 2019-04-28 14:56
 */
public interface OrderService {

    /**
     * 保存订单信息的接口
     * @param orderDO
     * @return
     * @throws BusinessException
     */
    public OrderDO saveOrder(OrderDO orderDO) throws BusinessException;

    /**
     * 获取所有的订单信息
     * @return
     */
    public List<OrderDO> getAllOrder();

    /**
     * 根据司机id获取
     * @return
     */
    public List<OrderDO> getByDriverId(Integer driverId);

    /**
     * 改变订单状态为已接
     *  0:未接
     *  1:已接
     *  2:已完成
     */
    public void changeOrderStatus2Accept(Integer orderId) throws BusinessException;

    /**
     * 改变订单状态为已完成
     *  0:未接
     *  1:已接
     *  2:已完成
     */
    public void changeOrderStatus2Finish(Integer orderId) throws BusinessException;
    /**
     * 改变订单状态为未接
     *  0:未接
     *  1:已接
     *  2:已完成
     */
    public void changeOrderStatus2Pre(Integer orderId) throws BusinessException;

    public List<OrderDO> getReceiveOrder();
}
