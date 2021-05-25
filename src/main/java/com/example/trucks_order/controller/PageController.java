package com.example.trucks_order.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author lmwis on 2019-04-28 8:48
 */
@Controller
@RequestMapping("page")
public class PageController {
    @RequestMapping("index")
    public String index(){
        return "home_page_index";
    }
    @RequestMapping("user_login")
    public String userLogin(){
        return "user_login_page";
    }
    @RequestMapping("driver_login")
    public String driverLogin(){
        return "driver_login_page";
    }
    @RequestMapping("online_order")
    public String onlineOrder(){
        return "online_order";
    }
}
