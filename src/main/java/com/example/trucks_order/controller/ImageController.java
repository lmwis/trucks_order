package com.example.trucks_order.controller;

import com.example.trucks_order.response.CommonReturnType;
import com.example.trucks_order.service.OrderService;
import com.example.trucks_order.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: Conference
 * @description: 图像拦截器
 * @author: smile丶
 * @create: 2019-02-20 18:10
 **/

@Controller
@RequestMapping("/imgs")
@CrossOrigin(allowCredentials = "true",allowedHeaders = "*")
public class ImageController extends BaseController{
    @Autowired
    OrderService orderService;
//    @Autowired
//    ConferenceService conferenceService;

//    String basepath= "/home/fehead/resources/";
    String basepath= "D:\\trucks_order_img\\";

    /**
     * 上传图片
     *
     *
     */

    //获取当前日期时间的string类型用于文件名防重复
    public String dates(){
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    //文件上传返回地址
    public String upimg(MultipartFile multipartFile, String uptype) throws IOException {
            //获取文件名
            String originalFilename = multipartFile.getName();
            //获取新文件名
            String newFileName= StringUtil.reName(originalFilename);
            File file  =  new File(basepath+newFileName);
            //上传文件
            multipartFile.transferTo(file);
            //将图片在项目中的地址和isok状态储存为json格式返回给前台，由于公司项目中没有fastjson只能用这个
            String address = "http://localhost:8091/trucks_order/imgs/getImg/"+newFileName;
            return address;
        }
    /**
     * url  /imgs/upload_img
     * @param request
     * @param response
     * @return 图片服务器地址json
     * @throws Exception
     */
    @RequestMapping(value = "/upload_img",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
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

//    @PostMapping("/uploadd_img") // 等价于 @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    public String upload(HttpServletRequest req, @RequestParam("file") MultipartFile file) {//1. 接受上传的文件  @RequestParam("file") MultipartFile file
//        try {
//            //2.根据时间戳创建新的文件名，这样即便是第二次上传相同名称的文件，也不会把第一次的文件覆盖了
//            String fileName = System.currentTimeMillis() + file.getOriginalFilename();
//            //3.通过req.getServletContext().getRealPath("") 获取当前项目的真实路径，然后拼接前面的文件名
//            String destFileName = "D://" + fileName;
//            //4.第一次运行的时候，这个文件所在的目录往往是不存在的，这里需要创建一下目录（创建到了webapp下uploaded文件夹下）
//            File destFile = new File(destFileName);
//            destFile.getParentFile().mkdirs();
//            //5.把浏览器上传的文件复制到希望的位置
//            file.transferTo(destFile);
//            //6.把文件名放在model里，以便后续显示用
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//            System.out.println("上传失败," + e.getMessage());
//        } catch (IOException e) {
//            e.printStackTrace();
//            System.out.println("上传失败," + e.getMessage());
//        }
//
//        return "showImg";
//    }

    private final ResourceLoader resourceLoader;


    @Autowired
    public ImageController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getImg/{filename:.+}")
    @ResponseBody
    public ResponseEntity<?> getFile(@PathVariable String filename) {
        try {
            return ResponseEntity.ok(resourceLoader.getResource("file:" + Paths.get(basepath, filename).toString()));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

//    /**
//     *
//     * @param request
//     * @param response
//     * @return 图片服务器地址
//     * @throws Exception
//     */
//    @RequestMapping("/wx_upload_user")
//    @ResponseBody
//    public CommonReturnType uploadPicture2(HttpServletRequest request, HttpServletResponse response) throws Exception {
//        //获取从前台传过来得图片
//        MultipartHttpServletRequest req =(MultipartHttpServletRequest)request;
//        MultipartFile multipartFile =  req.getFile("file");
//        String address = upimg(multipartFile,"user");
//        return JsonUtil.objectToJson(address);
//    }

}
