package com.mi.module.blog.controller;

import com.mi.data.vo.Pager;
import com.mi.module.blog.service.IArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 页面初始化插件控制
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017/9/10.
 */
@RestController
@RequestMapping("/initPage")
public class PagerController {
    @Autowired
    private IArticleService iArticleService;

    /**
     * 初始化主页分页信息
     **/
    @RequestMapping("/articles")
    @ResponseBody
    public Pager loadArticlePager(Pager pager) {
        iArticleService.initPage(pager);
        return pager;
    }

}
