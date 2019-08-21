package com.xuecheng.mange_cms.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CmsPageServiceTest {
    @Autowired
    private CmsPageService cmsPageService;

    @Test
    public void html(){
        String pageId="5d31961dc7109806d073bb48";
        String s = cmsPageService.generateHtml(pageId);
        System.out.println(s);
    }
}
