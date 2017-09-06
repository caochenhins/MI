package com.mi.module.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mi.data.vo.Pager;
import com.mi.data.vo.TagCloudVo;
import com.mi.data.vo.TypeVo;
import com.mi.module.blog.entity.*;
import com.mi.module.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.tags.form.InputTag;

import java.util.*;

/**
 * 页面路由控制器 - 展示主页
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017/7/9.
 */
@Controller
public class PageRouteController {

    @Autowired
    private IUserInfoService iUserInfoService;

    @Autowired
    private IFriendlinkService iFriendlinkService;

    @Autowired
    private IArticleService iArticleService;

    @Autowired
    private ITagService iTagService;

    @Autowired
    private ITypeService iTypeService;


    /******************** 博客主页 ********************/

    /**
     * 首页
     **/
    @RequestMapping("/")
    public String home(Model model) {
        //后期参数定义 做成多博客系统
        UserInfo uInfo = iUserInfoService.selectByUserId("1");
        model.addAttribute("userInfo", uInfo);

        //友情链接
        List<Friendlink> fLinkList = iFriendlinkService.selectAllList();
        model.addAttribute("fLinkList", fLinkList);

        //类别
        List<TypeVo> typeList = iTypeService.initTypeList();
        model.addAttribute("typeList", typeList);


        EntityWrapper<Article> exA = new EntityWrapper<>();
        Article article = new Article();
        article.setStatus(1);
        exA.setEntity(article);
        model.addAttribute("articleCount", iArticleService.selectCount(exA));

        model.addAttribute("tagCount", iTagService.selectCount(null));


        return "blog/index";
    }


    /**
     * 主页中部文章归档列表
     **/
    @RequestMapping("/main")
    public String main(Model model) {

        //获取归档列表
        List<Map> archiveList = iArticleService.selectArticleArchiveList();
        model.addAttribute("archiveList", archiveList);

        return "blog/main";
    }

    /**
     * 初始化主页分页信息
     **/
    @RequestMapping("/home/articles/load")
    @ResponseBody
    public Pager loadArticlePager(Pager pager) {
        iArticleService.initPage(pager);
        return pager;
    }


    /**
     * 标签云
     **/
    @RequestMapping("/tag/cloud")
    public String tagCloud(Model model) {

        List<Tag> tagList = iTagService.selectList(null);

        //参数所需
        List<TagCloudVo> mapList = new ArrayList<>();

        for (Tag t : tagList) {
            TagCloudVo tv = new TagCloudVo();
            Random random = new Random();

            tv.setTagId(t.getTagId());
            tv.setTagName(t.getTagName());
            tv.setPosition("?x=" + getRandomNum(-300, 300)
                    + "&y=" + getRandomNum(-300, 300)
                    + "&z=" + getRandomNum(-300, 300));

            mapList.add(tv);
        }

        model.addAttribute("tagList", mapList);

        return "blog/cloud/tagCloud";
    }

    /**
     * 关于我
     **/
    @RequestMapping("/about/me")
    public String aboutMe() {
        return "blog/aboutMe";
    }


    /**
     * thymeleaf 使用介绍
     **/
    @RequestMapping("/thymeleaf")
    public String thymeleafPage() {
        return "blog/thymeleaf";
    }


    // 获得一个给定范围的随机整数
    public static int getRandomNum(int smallistNum, int BiggestNum) {
        Random random = new Random();
        return (Math.abs(random.nextInt()) % (BiggestNum - smallistNum + 1)) + smallistNum;
    }

}
