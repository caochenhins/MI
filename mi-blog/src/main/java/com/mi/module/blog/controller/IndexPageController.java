package com.mi.module.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.mi.data.vo.ArticleVo;
import com.mi.data.vo.Pager;
import com.mi.module.blog.entity.Article;
import com.mi.module.blog.entity.Friendlink;
import com.mi.module.blog.entity.UserInfo;
import com.mi.module.blog.service.*;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * 页面路由控制器 - 展示主页
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017/7/9.
 */
@Controller
@Log4j
public class IndexPageController {
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


    /**
     * 加载分页列表数据
     *
     * @param model
     * @return
     */
    @RequestMapping("/article/list")
    public String loadArticle(Model model) {

        Page<ArticleVo> page;
        page = iArticleService.selectArticleList(new Page(1, 1));
        model.addAttribute("page", page);

        return "blog/main";
    }

    /**
     * 加载文章详细
     * 包括总标签数
     * 总文章数量
     * 分类及每个分类文章数量
     * 友链集合
     *
     * @return
     */
    @RequestMapping("/article/details/{articleId}")
    public String loadArticle(@PathVariable String articleId, Model model) {
        //当前文章的所有信息
        //后期参数定义 做成多博客系统
        UserInfo uInfo = iUserInfoService.selectByUserId("1");
        model.addAttribute("userInfo", uInfo);


        //友情链接
        List<Friendlink> fLinkList = iFriendlinkService.selectAllList();
        model.addAttribute("fLinkList", fLinkList);


        //新增判断，当文章不存在或文章不展示的情况下，会跳转到404页面
        ArticleVo articleVo = iArticleService.getArticleCustomById(articleId);
        if (articleVo == null) {
            return "redirect:/404";
        }
        model.addAttribute("article", articleVo);

        EntityWrapper<Article> exA = new EntityWrapper<>();
        Article article = new Article();
        article.setStatus(1);
        exA.setEntity(article);
        model.addAttribute("articleCount", iArticleService.selectCount(exA));

        model.addAttribute("tagCount", iTagService.selectCount(null));

        //上一篇
        Article lastArticle = iArticleService.getLastArticle(articleId);
        model.addAttribute("lastArticle", lastArticle);

        //下一篇
        Article nextArticle = iArticleService.getNextArticle(articleId);
        model.addAttribute("nextArticle", nextArticle);

        //增加浏览量
        iArticleService.addArticleCount(articleId);

        return "blog/article";
    }


    /**
     * 获取某个类别标签的分页文章
     *
     * @param model
     * @param pager
     * @param typeId
     * @return
     */
    @RequestMapping("/type/list/{typeId}")
    public String loadArticleByCategory(Model model, Pager pager, @PathVariable String typeId) {

        model.addAttribute("typeId", typeId);
        List<ArticleVo> articleList = iArticleService.selectArticleByType(pager, typeId);
        if (!articleList.isEmpty()) {
            model.addAttribute("articleList", articleList);
            model.addAttribute("pager", pager);
            model.addAttribute("typeName", articleList.get(0).getTypeList().get(0).getTypeName());
        }
        return "blog/type";
    }



}
