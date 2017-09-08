package com.mi.module.blog.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mi.data.vo.ArticleVo;
import com.mi.data.vo.Pager;
import com.mi.data.vo.TypeVo;
import com.mi.module.blog.entity.Type;
import com.mi.module.blog.mapper.ArticleMapper;
import com.mi.module.blog.mapper.TypeMapper;
import com.mi.module.blog.service.ITypeService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 
 * 文章类型; InnoDB free: 11264 kB 服务实现类
 *
 * @author yesh
 *         (M.M)!
 *         Created by 2017-07-09.
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements ITypeService {

    @Resource
    private TypeMapper typeMapper;

    @Resource
    private ArticleMapper articleMapper;


    @Override
    public void initPage(Pager pager) {
        EntityWrapper<Type> ew = new EntityWrapper<>();
        int count = typeMapper.selectCount(ew);
        pager.setTotalCount(count);
    }

    @Override
    public List<Type> load(Pager pager, String param) {
        pager.setStart(pager.getStart());
        return typeMapper.load(pager,param);
    }

    @Override
    public boolean checkExist(Type type) {
        int count = typeMapper.checkExist(type);
        if (count > 0){
            return true;
        }
        return false;
    }

    @Override
    public List<TypeVo> initTypeList() {
        return typeMapper.initTypeList();
    }

    @Override
    public List<ArticleVo> selectArticleByType(Pager pager, String typeId) {
        pager.getStart();
        return articleMapper.selectArticleByType(pager, typeId);
    }
}
