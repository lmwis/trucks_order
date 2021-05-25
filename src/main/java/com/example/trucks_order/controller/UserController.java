package com.example.trucks_order.controller;

import com.alibaba.druid.util.StringUtils;
import com.example.trucks_order.controller.viewobject.UserVO;
import com.example.trucks_order.dataobject.DriverDO;
import com.example.trucks_order.dataobject.UserDO;
import com.example.trucks_order.error.BusinessException;
import com.example.trucks_order.error.EmBusinessError;
import com.example.trucks_order.response.CommonReturnType;
import com.example.trucks_order.service.DriverService;
import com.example.trucks_order.service.UserService;
import com.example.trucks_order.service.model.UserModel;
import com.example.trucks_order.util.CookieUtil;
import com.example.trucks_order.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author lmwis on 2019-04-09 14:19
 */
@RestController("/user")
@RequestMapping("/user")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class UserController extends BaseController{
    @Autowired
    UserService userService;

    @Autowired
    DriverService driverService;

    @RequestMapping(value = "login",method = RequestMethod.POST)
    public CommonReturnType login(HttpServletResponse response, HttpServletRequest request, String email, String password, String user_type) throws BusinessException {
        //判空处理
        if(StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        // 用户登录流程
        UserModel userModel = new UserModel();
        userModel.setEmail(email);
        userModel.setPassword(password);

        if(StringUtils.equals(user_type,"user")){   //用户登录处理方法
            //验证登录状态
            Cookie[] cookies = request.getCookies();
            Cookie c1 = CookieUtil.getCookieFromCookies(cookies,"user_id");
            Cookie c2 = CookieUtil.getCookieFromCookies(cookies,"user_nickname");
            //校验user_id和user_nickname
            if(c1 != null && c2!=null){
                String userId = c1.getValue();
                String userNickname = c2.getValue();
                if((!StringUtils.isEmpty(userId)) && (!StringUtils.isEmpty(userNickname))){//存在登录状态
                    System.out.println("已经登录！！");
                }
            }
            HttpSession session = request.getSession();

//        boolean result = userService.login(userModel);
            //写入数据库
            UserDO userDO= userService.login(userModel);
            if (userDO!=null) {
                //写入session
                session.setAttribute("userModel", userModel);
                session.setMaxInactiveInterval(30*60);
            }

            //cookie
//        Cookie cookieUserId = new Cookie("user_id",userDO.getUserId().toString());
            Cookie cookieNickname = new Cookie("user_nickname",userDO.getNickname());
//        response.addCookie(cookieUserId);
            response.addCookie(cookieNickname);
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            return CommonReturnType.create(userDO);
        }else{   //司机登录处理方法

            DriverDO driverDO = driverService.login(userModel);

            HttpSession session = request.getSession();
            if (driverDO!=null) {
                //写入session
                session.setAttribute("driverModel", driverDO);
                session.setMaxInactiveInterval(30*60);
            }
            //写入cookie
            Cookie driverName = new Cookie("driver_name",driverDO.getDriverName());
            response.addCookie(driverName);
            response.setContentType("text/html;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            return CommonReturnType.create(driverDO);
        }


    }
    @PostMapping(value = "register",produces = "application/json;charset=utf-8")
    public CommonReturnType register(String nickname,String email,String password) throws BusinessException {

        //判空处理
        if(StringUtils.isEmpty(nickname) || StringUtils.isEmpty(email) || StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }

        //用户注册流程
        UserModel userModel = new UserModel();
        userModel.setNickname(nickname);
        userModel.setEmail(email);
        userModel.setPassword(password);
        UserVO userVO = userService.register(userModel);

        return CommonReturnType.create(userVO);
    }

    @GetMapping("test")
    public String test() {
        return "test";
    }
}
