package com.example.trucks_order.mapper;

import com.example.trucks_order.dataobject.ConnecDO;
import com.example.trucks_order.dataobject.DriverDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DriverDOMapper {

    List<DriverDO> selectAll();
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driver_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int deleteByPrimaryKey(Integer driverId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driver_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int insert(DriverDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driver_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int insertSelective(DriverDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driver_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    DriverDO selectByPrimaryKey(Integer driverId);

    DriverDO selectByName(String driverName);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driver_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int updateByPrimaryKeySelective(DriverDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table driver_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int updateByPrimaryKey(DriverDO record);

    void insertConnect(ConnecDO connecDO);

    void delConnect(ConnecDO connecDO);

    int updateByPrimaryKeyOnlyInfo(DriverDO record);
}