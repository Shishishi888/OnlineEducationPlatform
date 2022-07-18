package com.tjulab.cmsservice.service.impl;

import com.tjulab.cmsservice.entity.CmsBanner;
import com.tjulab.cmsservice.mapper.CmsBannerMapper;
import com.tjulab.cmsservice.service.CmsBannerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
     * 查询所有的banner
     * @return
     */
    @Override
    public List<CmsBanner> getAllBanner() {
        List<CmsBanner> cmsBannerList = baseMapper.selectList(null);
        return cmsBannerList;
    }
}
