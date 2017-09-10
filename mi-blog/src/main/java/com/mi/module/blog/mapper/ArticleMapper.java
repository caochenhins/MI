package com.mi.module.blog.mapper;

import com.mi.data.vo.ArticleVo;
import com.mi.data.vo.Pager;
import com.mi.module.blog.entity.Article;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *
 * 文章; InnoDB free: 11264 kB Mapper 接口
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017-07-09.
 */
public interface ArticleMapper extends BaseMapper<Article> {

    List<Map> selectArticleArchiveList();

    List<Article> selectArticleListByKeywords(Map map);

    List<ArticleVo> selectArticleList(Pager<Article> pager);

    List<Article> loadArticle(Map<String, Object> param);

    ArticleVo getArticleCustomById(String articleId);

    Article getLastArticle(String articleId);

    Article getNextArticle(String articleId);

    void addArticleCount(String articleId);

    List<ArticleVo> selectArticleByType(@Param("pager") Pager pager, @Param("typeId") String typeId);

}