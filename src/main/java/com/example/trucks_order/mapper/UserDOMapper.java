package com.example.trucks_order.mapper;

import com.example.trucks_order.dataobject.UserDO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDOMapper {

    List<UserDO> selectAll();

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int deleteByPrimaryKey(Integer userId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int insert(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int insertSelective(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    UserDO selectByPrimaryKey(Integer userId);

    UserDO selectByEmail(String email);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int updateByPrimaryKeySelective(UserDO record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbg.generated Tue Apr 09 14:54:21 CST 2019
     */
    int updateByPrimaryKey(UserDO record);
}