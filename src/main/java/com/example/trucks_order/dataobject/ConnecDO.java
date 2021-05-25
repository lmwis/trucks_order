package com.example.trucks_order.dataobject;

/**
 * @author lmwis on 2019-04-28 20:06
 */
public class ConnecDO {
    Integer id;
    Integer driverId;
    Integer orderId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
