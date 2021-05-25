package com.example.trucks_order.controller;

import com.example.trucks_order.dataobject.OrderDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.response.CommonReturnType;
import com.example.trucks_order.service.OrderService;
import com.example.trucks_order.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author lmwis on 2019-04-26 20:39
 */
@RestController("order")
@RequestMapping("/order")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class OrderController {

    @Autowired
    OrderService orderService;

    String filePath="D:\\trucks_order_img\\";

    @RequestMapping(value = "/uploadDataImg",method = RequestMethod.POST)
    @ResponseBody
    public CommonReturnType uploadData(MultipartFile file) throws BusinessException {
        if(file.isEmpty()){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,"文件不存在");
        }
        String fileName = file.getOriginalFilename();

        String path = filePath+ StringUtil.reName(fileName);
        System.out.println(path);
        File dest = new File(path);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
        } catch (IOException e) {
            e.printStackTrace();
        }

        return CommonReturnType.create(path);
    }
    @RequestMapping(value = "publishOrder",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public CommonReturnType publishOrder(@RequestParam("item_count")String itemCount,
                                         @RequestParam("item_volume")String itemVolume,
                                         @RequestParam("order_img_path")String orderImgPath,
                                         @RequestParam("truck_type")String truckType,
                                         @RequestParam("order_time_line")String orderTimeLine,
                                         @RequestParam("order_time")String orderTime,
                                         @RequestParam("order_start")String orderStart,
                                         @RequestParam("order_end")String orderEnd,
                                         @RequestParam("contact")String contact,
                                         @RequestParam("contact_tel")String contactTel,
                                         @RequestParam("product_desc")String productDesc,
                                         @RequestParam("price")String price) throws BusinessException {

        //模型封装
        OrderDO orderDO = new OrderDO();
        orderDO.setItemCount(Integer.parseInt(itemCount));
        orderDO.setItemVolume(itemVolume);
        orderDO.setItemImgPath(orderImgPath);
        orderDO.setOrderTimeline(orderTimeLine);
        orderDO.setCarType(Integer.parseInt(truckType));
        orderDO.setOrderStartPosition(orderStart);
        orderDO.setOrderEndPosition(orderEnd);
        orderDO.setPrice(BigDecimal.valueOf(Double.valueOf(price)));
        orderDO.setLinkmanName(contact);
        orderDO.setLinkmanTel(contactTel);
        orderDO.setOrderTime(orderTime);
        orderDO.setItemDesc(productDesc);

        //调用接口存入数据库
        OrderDO orderDO1 = orderService.saveOrder(orderDO);

        //返回数据
        return CommonReturnType.create(orderDO1);
    }

    /**
     * 获取所有订单信息
     * @return
     */
    @RequestMapping(value = "getAll",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public CommonReturnType getAll(){
        return CommonReturnType.create(orderService.getAllOrder());
    }
    /**
     * 获取指定司机的订单
     * @return
     */
    @RequestMapping(value = "getMy",method = RequestMethod.GET,produces = "application/json;charset=utf-8")
    public CommonReturnType getMy(String driver_id){
        return CommonReturnType.create(orderService.getByDriverId(Integer.parseInt(driver_id)));
    }
}
