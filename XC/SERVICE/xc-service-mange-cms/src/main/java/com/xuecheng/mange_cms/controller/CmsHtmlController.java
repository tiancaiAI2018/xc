package com.xuecheng.mange_cms.controller;

import com.xuecheng.api.cms.CmsHtmlControllerApi;
import com.xuecheng.framework.web.BaseController;
import com.xuecheng.mange_cms.service.CmsPageService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletOutputStream;
import java.io.IOException;

@Controller
@RequestMapping("cms/html")
public class CmsHtmlController extends BaseController implements CmsHtmlControllerApi {
    @Autowired
    private CmsPageService cmsPageService;
    @GetMapping("/preview/{id}")
    @Override
    public void previewHtml(@PathVariable("id") String pageId) {
        String html = cmsPageService.generateHtml(pageId);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            IOUtils.write(html,outputStream,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
