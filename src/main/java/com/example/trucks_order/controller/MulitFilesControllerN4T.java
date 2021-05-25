package com.example.trucks_order.controller;

import com.example.trucks_order.response.CommonReturnType;
import com.example.trucks_order.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

/**
 * @author lmwis on 2019-06-15 17:10
 */
@Controller
@RequestMapping("/files")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class MulitFilesControllerN4T {

    private static final String basepath = "D:\\批处理\\";

    @RequestMapping(value = "/upload_file",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public CommonReturnType uploadPicture1(HttpServletRequest request, HttpServletResponse response,
                                           @RequestParam("file") MultipartFile multipartFile) throws Exception {
        System.out.println(multipartFile);
        String fileName= StringUtil.reName(multipartFile.getOriginalFilename());
        System.out.println(fileName);

        File file  =  new File(basepath + fileName);
        //上传文件
        multipartFile.transferTo(file);

        String address = "http://localhost:8091/trucks_order/imgs/getImg/"+fileName;

        return CommonReturnType.create(address);
    }

}
