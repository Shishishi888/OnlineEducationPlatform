package com.tjulab.cmsservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.cmsservice.entity.CmsBanner;
import com.tjulab.cmsservice.mapper.CmsBannerMapper;
import com.tjulab.cmsservice.service.CmsBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-19
 */
@Service
public class CmsBannerServiceImpl extends ServiceImpl<CmsBannerMapper, CmsBanner> implements CmsBannerService {

    /**
     * 查询banner的前两条记录
     * @return
     */
    @Cacheable(key = "'bannerList'", value = "cmsBannerList")
    @Override
    public List<CmsBanner> getBannerList() {
        QueryWrapper<CmsBanner> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");  // 按照banner的id进行降序排序
        queryWrapper.last("limit 2");  // 拼接sql语句，只查询banner的前两条记录
        List<CmsBanner> cmsBannerList = baseMapper.selectList(queryWrapper);
        return cmsBannerList;
    }
}
