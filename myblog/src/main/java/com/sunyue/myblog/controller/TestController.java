package com.sunyue.myblog.controller;
import com.sunyue.myblog.commons.MdToHtml;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class TestController {

    /**上传地址*/
    //@Value("${upload.picture.path}")
    private String filePath;

    // 跳转上传页面
    @RequestMapping(value = "test",method = RequestMethod.GET)
    public String test(Model model) {
        model.addAttribute("mk","#### 111\n" +
                "```asp\n" +
                "asp\n" +
                "```\n" +
                "```cpp\n" +
                "c++\n" +
                "```\n" +
                "![](../images/b7bd834a-7cb0-482a-9dc8-0ba316416ebb.jpg)");
        return "edited";
    }
    @RequestMapping(value = "test",method = RequestMethod.POST)
    public String test1(HttpServletRequest request) {
        System.out.println(request.getParameter("editormd-html-textarea"));
        System.out.println(request.getParameter("test-editormd-markdown-doc"));
        System.out.println(MdToHtml.convert(request.getParameter("test-editormd-markdown-doc")));
        return "";
    }
    // 执行上传
    @RequestMapping("upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        // 获取上传文件名
        String filename = file.getOriginalFilename();
        // 定义上传文件保存路径
        String path = filePath+"rotPhoto/";
        // 新建文件
        File filepath = new File(path, filename);
        // 判断路径是否存在，如果不存在就创建一个
        if (!filepath.getParentFile().exists()) {
            filepath.getParentFile().mkdirs();
        }
        try {
            // 写入文件
            file.transferTo(new File(path + File.separator + filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将src路径发送至html页面
        model.addAttribute("filename", "/images/rotPhoto/"+filename);
        return "test";
    }
}