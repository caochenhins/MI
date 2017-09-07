package com.mi.module.blog.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mi.data.vo.ArticleVo;
import com.mi.data.vo.Pager;
import com.mi.module.blog.entity.Article;
import com.mi.module.blog.entity.Friendlink;
import com.mi.module.blog.entity.UserInfo;
import com.mi.module.blog.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 文章; InnoDB free: 11264 kB 控制器
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017-07-09.
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    IArticleService iArticleService;
    @Autowired
    IFriendlinkService iFriendlinkService;
    @Autowired
    ITypeService iTypeService;
    @Autowired
    ITagService iTagService;
    @Autowired
    IUserInfoService iUserInfoService;

    /**
     * 加载分页列表数据
     *
     * @param model
     * @return
     */
    @RequestMapping("/load")
    public String loadArticle(Pager<Article> pager, Model model) {
        List<ArticleVo> articleList = iArticleService.selectArticleList(pager);
        model.addAttribute("articleList", articleList);
        return "blog/right/articleSummary";
    }


    /**
     * 加载文章
     * 包括总标签数
     * 总文章数量
     * 分类及每个分类文章数量
     * 友链集合
     *
     * @return
     */
    @RequestMapping("/details/{articleId}")
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
     * 全局搜索
     *
     * @param keyword 关键字
     * @param model
     * @return
     */
    @RequestMapping("/content/search")
    public String search(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        System.err.println("" + keyword);
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("keyword", keyword);
        List<Article> articleList = iArticleService.selectArticleListByKeywords(paramMap);
        model.addAttribute("articleList", articleList);
        return "blog/part/search-info";
    }

}
