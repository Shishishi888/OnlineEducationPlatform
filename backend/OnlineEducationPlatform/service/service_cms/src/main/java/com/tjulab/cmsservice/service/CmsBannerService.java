package com.tjulab.cmsservice.service;

import com.tjulab.cmsservice.entity.CmsBanner;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 首页banner表 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-19
 */
public interface CmsBannerService extends IService<CmsBanner> {

    /**
     * 查询banner的前两条记录
     * @return
     */
    List<CmsBanner> getBannerList();
}
