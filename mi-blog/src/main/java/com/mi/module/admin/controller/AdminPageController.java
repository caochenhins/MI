package com.mi.module.admin.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mi.module.blog.entity.Tag;
import com.mi.module.blog.entity.Type;
import com.mi.module.blog.entity.UserInfo;
import com.mi.module.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 页面路由控制器 - 后台
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017/9/6.
 */
@Controller
public class AdminPageController {
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

    /******************** 博客后台 ********************/

    /**
     * 登录页面
     **/
    @RequestMapping("/login")
    public String loginPage() {
        return "login";
    }



    /**
     * 跳转到分类列表页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/admin/type/list")
    public String typePage(Model model) {
        UserInfo userInfo = iUserInfoService.selectByUserId("1");
        model.addAttribute("userInfo", userInfo);
        return "admin/type/typeList";
    }

    /**
     * 跳转标签展示页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/admin/tag/list")
    public String tagPage(Model model) {
        UserInfo userInfo = iUserInfoService.selectByUserId("1");
        model.addAttribute("userInfo", userInfo);
        return "admin/tag/tagList";
    }

    /**
     * 文章首页
     **/
    @RequestMapping("/admin/article/list")
    public String articlePage(Model model) {
        EntityWrapper<Tag> eTag = new EntityWrapper();
        EntityWrapper<Type> eType = new EntityWrapper();

        List<Tag> tagList = iTagService.selectList(eTag);
        List<Type> typeList = iTypeService.selectList(eType);
        UserInfo userInfo = iUserInfoService.selectByUserId("1");

        model.addAttribute("userInfo", userInfo);
        model.addAttribute("tagList", tagList);
        model.addAttribute("typeList", typeList);
        return "admin/article/articleList";
    }

    /**
     * 后台首页（文章）
     **/
    @RequestMapping("/admin/home")
    public String homePage() {
        return "redirect:/admin/index";
    }

    /**
     * 后台首页内容
     **/
    @RequestMapping("/admin/index")
    public String mainPage(Model model) {
        UserInfo userInfo = iUserInfoService.selectByUserId("1");
        model.addAttribute("userInfo", userInfo);
        return "admin/index";
    }

}
