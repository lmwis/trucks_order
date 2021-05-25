package com.example.trucks_order.service;

import com.example.trucks_order.dataobject.ConnecDO;
import com.example.trucks_order.dataobject.DriverDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.service.model.UserModel;

/**
 * @author lmwis on 2019-04-28 15:36
 */
public interface DriverService {
    /**
     * 司机注册
     * @param driverDO
     * @return
     * @throws BusinessException
     */
    public DriverDO register(DriverDO driverDO) throws BusinessException;

    /**
     * 司机登录
     * @param userModel
     * @return
     * @throws BusinessException
     */
    public DriverDO login(UserModel userModel)throws BusinessException;

    /**
     * 添加订单
     * @return
     * @throws BusinessException
     */
    public DriverDO addOrder(ConnecDO connecDO)throws BusinessException;

    /**
     * 完成订单
     * @param orderId
     */
    public void finishOrder(Integer orderId) throws BusinessException;
    /**
     * 取消订单
     * @param orderId
     */
    public void cancelOrder(Integer driverId,Integer orderId) throws BusinessException;
}
