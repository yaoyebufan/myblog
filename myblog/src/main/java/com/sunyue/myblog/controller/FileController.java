package com.sunyue.myblog.controller;

import com.sunyue.myblog.commons.EmptyUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 */
@CrossOrigin
@Controller
public class FileController {
    // 获取配置文件中图片的路径
    @Value("${cbs.imagesPath}")
    private String UPLOADED_FOLDER;
    /*
    * 以下全为相对路径*/
    //本地图片地址
    //private static String UPLOADED_FOLDER = "src/main/resources/static/upload/";
    //jar包方式部署服务器地址
    //private static String UPLOADED_FOLDER = "upload/";
    //docker-compose方式部署服务器地址
    //private static String UPLOADED_FOLDER = "src/main/resources/static/upload/";

    @GetMapping(value = "/file")
    public String file() {
        return "fileup";
    }

    private Map<String, Object> deleteFileUtil(String params) {
        /*进来的是一个/images/5770871d-3e20-4f62-804e-f17f0ac0d562.jpg虚拟地址，对应实际地址需要截取处理*/
        params = params.substring(params.lastIndexOf("/") + 1);
        Map<String, Object> map = new HashMap<String, Object>();
        String url = UPLOADED_FOLDER + params;
        //获取相对路径暂时弃用
        //File file = new File(url);
        //String path = file.getAbsolutePath();
        File filedel = new File(url);
        if (!filedel.exists()) {
            map.put("success", 0);
            map.put("message", "文件不存在！");
        } else {
            map.put("success", 1);
            map.put("message", "删除成功！");
            filedel.delete();
        }
        return map;
    }

    private Map<String, Object> uploadFileUtil(MultipartFile file) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (file.isEmpty()) {
            map.put("success", 0);
            map.put("message", "文件不存在");
            return map;
        }

        // 后缀名...不需要后缀名在压缩操作中加入，如果没有压缩操作则需要但是文章插图时有个校验需要删除存在问题
        //String fileName = file.getOriginalFilename();  // 文件名
        //String suffixName = fileName.substring(fileName.lastIndexOf("."));
        //fileName = UUID.randomUUID() + suffixName; // 新文件名
        File fileDir = new File(UPLOADED_FOLDER);
        String fileName = UUID.randomUUID().toString();
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        try {
            try {
                /*先尝试压缩并保存图片*/
                Thumbnails.of(file.getInputStream()).scale(0.5f)
                        .outputQuality(0.8)
                        //全部输出为jpg格式
                        .outputFormat("jpg")
                        .toFile(UPLOADED_FOLDER + "/" + fileName);
            } catch (IOException e) {

            }
            /*不压缩文件如下*/
            //file.transferTo(new File(path, fileName));
            map.put("success", 1);
            map.put("message", "上传成功！");
            //上面压缩的所有后缀名都为jpg
            map.put("url", "/images/" + fileName+".jpg");
            /*这样也可以*/
            //map.put("url", "../images/" + fileName);
        } catch (
                Exception e) {
            map.put("success", 0);
            map.put("message", "上传失败！");
            e.printStackTrace();
        }
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
    public Map<String, Object> FileUpload(@RequestParam("file") MultipartFile file) {
        return uploadFileUtil(file);
    }

    @ResponseBody
    @RequestMapping(value = "/fileUpdate", method = RequestMethod.POST)
    public Map<String, Object> FileUpload1(@RequestParam("file") MultipartFile file, @RequestParam String params) {
        Map map = new HashMap<String, Object>();
        params = params.substring(params.lastIndexOf("/") + 1);
        if (EmptyUtil.isEmpty(params)) {
            map.put("success", 0);
            map.put("message", "路径为空！");
        } else {
            map = deleteFileUtil(params);
        }
        map = uploadFileUtil(file);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/fileDelete", method = RequestMethod.POST)
    public Object FileDelete(HttpServletRequest request) {
        String params = request.getParameter("params");
        Map map = new HashMap<String, Object>();
        if (EmptyUtil.isEmpty(params)) {
            map.put("success", 0);
            map.put("message", "路径为空！");
        }
        map = deleteFileUtil(params);
        return map;
    }


    @ResponseBody
    @RequestMapping(value = "/markdown", method = RequestMethod.POST)
    public Map<String, Object> demo(@RequestParam(value = "editormd-image-file", required = false) MultipartFile file, HttpServletRequest request) {
        return uploadFileUtil(file);

    }
}