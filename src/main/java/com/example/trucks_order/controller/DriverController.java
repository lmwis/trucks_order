package com.example.trucks_order.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.trucks_order.controller.viewobject.UserVO;
import com.example.trucks_order.dataobject.ConnecDO;
import com.example.trucks_order.dataobject.DriverDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.response.CommonReturnType;
import com.example.trucks_order.service.DriverService;
import com.example.trucks_order.service.model.UserModel;
import com.example.trucks_order.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author lmwis on 2019-04-28 15:23
 */
@RestController("driver")
@RequestMapping("/driver")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class DriverController extends BaseController{

    @Autowired
    DriverService driverService;

    /**
     * 司机注册
     * @param name
     * @param sex
     * @param age
     * @param tel
     * @param img1_path
     * @param img2_path
     * @param img3_path
     * @param img4_path
     * @param img5_path
     * @param password
     * @param passwordAgain
     * @return
     * @throws BusinessException
     */
    @PostMapping(value = "/register",produces = "application/json;charset=utf-8")
    public CommonReturnType register(String name, String sex, String age,
                                     String tel,String img1_path,String img2_path,
                                     String img3_path,String img4_path,String img5_path
                                     ,String password,String passwordAgain) throws BusinessException {

        //判空处理
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(password) || StringUtils.isEmpty(passwordAgain)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //此处将两次密码校验放入后台进行
        if(!StringUtils.equals(password,passwordAgain)){
            throw new BusinessException(EmBusinessError.LOGIN_ERROR,"两次密码输入不同");
        }

        //司机注册流程
        //封装模型
        DriverDO driverDO = new DriverDO();
        driverDO.setDriverName(name);
        Integer sexCode = StringUtils.equals(sex,"男")?1:2;
        driverDO.setDriverSex(sexCode);
        driverDO.setDriverAge(Integer.parseInt(age));
        driverDO.setPassword(password);
        driverDO.setIdcardImgPath(img1_path);
        driverDO.setDriverQualImgPath(img2_path);
        driverDO.setCarQualImgPath(img3_path);
        driverDO.setCarInsuranceImgPath(img4_path);
        driverDO.setCarLicenseImgPath(img5_path);

        //数据库存储
        DriverDO driverDO1 = driverService.register(driverDO);

        return CommonReturnType.create(driverDO1);
    }

    /**
     * 司机接单
     * @param driver_id
     * @param order_id
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "/addOrder",produces = "application/json;charset=utf-8")
    public CommonReturnType register(String driver_id,String order_id) throws BusinessException {

        //判空处理
        if(StringUtils.isEmpty(driver_id) || StringUtils.isEmpty(order_id)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //添加流程
        ConnecDO connecDO = new ConnecDO();
        connecDO.setDriverId(Integer.parseInt(driver_id));
        connecDO.setOrderId(Integer.parseInt(order_id));
        driverService.addOrder(connecDO);
        return CommonReturnType.create(null);
    }

    /**
     * 完成订单
     * @param order_id
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "/finishOrder",produces = "application/json;charset=utf-8")
    public CommonReturnType finishOrder(String order_id) throws BusinessException {

        //判空处理
        if(StringUtils.isEmpty(order_id)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        //完成订单流程
        driverService.finishOrder(Integer.parseInt(order_id));
        return CommonReturnType.create(null);
    }

    /**
     * 取消订单吗
     * @param driver_id
     * @param order_id
     * @return
     * @throws BusinessException
     */
    @GetMapping(value = "cancelOrder",produces = "application/json;charset=utf-8")
    public CommonReturnType cancelOrder(String driver_id,String order_id) throws BusinessException {

        //判空处理
        if(StringUtils.isEmpty(driver_id) || StringUtils.isEmpty(order_id)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //取消订单流程
        driverService.cancelOrder(Integer.parseInt(driver_id),Integer.parseInt(order_id));
        return CommonReturnType.create(null);
    }


}
