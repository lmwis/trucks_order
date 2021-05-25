package com.example.trucks_order.service;

import com.example.trucks_order.dataobject.DriverDO;
import com.example.trucks_order.dataobject.OrderDO;
import com.example.trucks_order.dataobject.UserDO;
import com.example.trucks_order.error.BusinessException;

import java.util.List;

/**
 * @author lmwis on 2019-05-04 12:29
 */
public interface AdminService {

    public boolean login(String username, String password) throws BusinessException;

    public void saveOrders(List<OrderDO> orderDOList);

    public void delOrder(Integer orderId);

    public List<UserDO> getAllUser();

    public void delUser(Integer userId);

    public void saveUsers(List<UserDO> userDOList);

    public List<DriverDO> getAllDriver();

    public void delDriver(Integer driverId);

    public void saveDriver(List<DriverDO> driverDOList);

}
