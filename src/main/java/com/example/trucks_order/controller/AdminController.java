package com.example.trucks_order.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.trucks_order.controller.viewobject.UserVO;
import com.example.trucks_order.dataobject.DriverDO;
import com.example.trucks_order.dataobject.OrderDO;
import com.example.trucks_order.dataobject.UserDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.response.CommonReturnType;
import com.example.trucks_order.service.AdminService;
import com.example.trucks_order.service.model.UserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lmwis on 2019-05-04 12:28
 */
@RestController("admin")
@RequestMapping("/admin")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class AdminController extends BaseController {

    @Autowired
    AdminService adminService;

    /**
     * 管理员登陆
     * @param res
     * @param username
     * @param password
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType login(HttpServletRequest res, @RequestParam("username") String username, @RequestParam("password") String password) throws BusinessException {
        if(StringUtils.isEmpty(username) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"用户名或密码为不能为空");
        }
        //boolean flag = userServiceV1.login(username,password);
        boolean flag = true;
        if(adminService.login(username,password)){
            HttpSession session = res.getSession();
            session.setMaxInactiveInterval(30*60);//以秒为单位，即在没有活动30分钟后，session将失效
            session.setAttribute("username", username);
            flag=false;
            return CommonReturnType.create(flag);
        }
        throw new BusinessException(EmBusinessError.LOGIN_ERROR,"账号或密码错误");
    }

    /**
     * 修改订单
     * @param orderId
     * @param status
     * @param imgs
     * @param itemCount
     * @param itemVolume
     * @param truckType
     * @param orderTimeLine
     * @param orderTime
     * @param orderStart
     * @param orderEnd
     * @param contact
     * @param contactTel
     * @param productDesc
     * @param price
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "saveOrderChange",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public CommonReturnType saveOrderChange(@RequestParam("order_id[]")String[] orderId,
                                            @RequestParam("status[]")String[] status,
                                            @RequestParam("imgs[]")String[] imgs,
                                            @RequestParam("item_count[]")String[] itemCount,
                                         @RequestParam("item_volume[]")String[] itemVolume,
                                         @RequestParam("truck_type[]")String[] truckType,
                                         @RequestParam("order_time_line[]")String[] orderTimeLine,
                                         @RequestParam("order_time[]")String[] orderTime,
                                         @RequestParam("order_start[]")String[] orderStart,
                                         @RequestParam("order_end[]")String[] orderEnd,
                                         @RequestParam("contact[]")String[] contact,
                                         @RequestParam("contact_tel[]")String[] contactTel,
                                         @RequestParam("product_desc[]")String[] productDesc,
                                         @RequestParam("price[]")String[] price) throws BusinessException {

        //模型封装
        List<OrderDO> orderDOList = new ArrayList<>();
        for(int i=0;i<orderId.length;i++){
            OrderDO orderDO = new OrderDO();
            orderDO.setOrderId(Integer.parseInt(orderId[i]));
            orderDO.setStatus(Integer.parseInt(status[i]));
            orderDO.setItemCount(Integer.parseInt(itemCount[i]));
            orderDO.setItemVolume(itemVolume[i]);
            orderDO.setOrderTimeline(orderTimeLine[i]);
            orderDO.setCarType(Integer.parseInt(truckType[i]));
            orderDO.setOrderStartPosition(orderStart[i]);
            orderDO.setOrderEndPosition(orderEnd[i]);
            orderDO.setPrice(BigDecimal.valueOf(Double.valueOf(price[i])));
            orderDO.setLinkmanName(contact[i]);
            orderDO.setLinkmanTel(contactTel[i]);
            orderDO.setOrderTime(orderTime[i]);
            orderDO.setItemDesc(productDesc[i]);
            orderDO.setItemImgPath(imgs[i]);
            orderDOList.add(orderDO);
        }
        adminService.saveOrders(orderDOList);
        //调用接口存入数据库
//        OrderDO orderDO1 = orderService.saveOrder(orderDO);

        //返回数据
        return CommonReturnType.create(null);
    }

    /**
     * 删除订单
     * @param orderId
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "delOrder",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public CommonReturnType delOrder(@RequestParam("order_id")String orderId) throws BusinessException {

        adminService.delOrder(Integer.parseInt(orderId));

        //返回数据
        return CommonReturnType.create(null);
    }

    /**
     * 获取所有用户
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "getAllUser",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public CommonReturnType getAllUser() throws BusinessException {

        //返回数据
        return CommonReturnType.create(adminService.getAllUser());
    }

    /**
     * 删除用户
     * @param userId
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "delUser",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public CommonReturnType delUser(@RequestParam("user_id")String userId) throws BusinessException {

        adminService.delUser(Integer.parseInt(userId));

        //返回数据
        return CommonReturnType.create(null);
    }

    /**
     * 修改用户
     * @param user_id
     * @param nickname
     * @param email
     * @param password
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "saveUserChange",produces = "application/json;charset=utf-8")
    public CommonReturnType saveUserChange(@RequestParam("user_id[]")String[] user_id,
                                           @RequestParam("nickname[]")String[] nickname,
                                           @RequestParam("email[]")String[] email,
                                           @RequestParam("password[]")String[] password) throws BusinessException {


        List<UserDO> userDOList = new ArrayList<>();
        for (int i = 0; i < user_id.length; i++) {
            UserDO userDO = new UserDO();
            userDO.setUserId(Integer.parseInt(user_id[i]));
            userDO.setEmail(email[i]);
            userDO.setNickname(nickname[i]);
            userDO.setPassword(password[i]);
            userDOList.add(userDO);
        }

        adminService.saveUsers(userDOList);

        return CommonReturnType.create(null);
    }
    @RequestMapping(value = "getAllDriver",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public CommonReturnType getAllDriver() throws BusinessException {

        //返回数据
        return CommonReturnType.create(adminService.getAllDriver());
    }

    @RequestMapping(value = "delDriver",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public CommonReturnType delDriver(@RequestParam("driver_id")String driverId) throws BusinessException {

        adminService.delDriver(Integer.parseInt(driverId));

        //返回数据
        return CommonReturnType.create(null);
    }

    @RequestMapping(value = "saveDriver",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public CommonReturnType saveDriver(@RequestParam("driver_id[]")String[] driverId,
                                       @RequestParam("driver_name[]")String[] driver_name,
                                       @RequestParam("driver_sex[]")String[] driver_sex,
                                       @RequestParam("driver_age[]")String[] driver_age,
                                       @RequestParam("driver_sex[]")String[] password) throws BusinessException {

        List<DriverDO> userDOList = new ArrayList<>();

        for(int i=0;i<driverId.length;i++){
            DriverDO driverDO = new DriverDO();
            driverDO.setDriverId(Integer.parseInt(driverId[i]));
            driverDO.setDriverName(driver_name[i]);
            driverDO.setDriverSex("男".equals(driver_sex[i])?0:1);
            driverDO.setDriverAge(Integer.parseInt(driver_age[i]));
            driverDO.setPassword(password[i]);
            userDOList.add(driverDO);
        }

        adminService.saveDriver(userDOList);

        //返回数据
        return CommonReturnType.create(null);
    }



}
