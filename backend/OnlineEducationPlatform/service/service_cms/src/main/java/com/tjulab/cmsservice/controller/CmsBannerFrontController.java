package com.tjulab.cmsservice.controller;

import com.tjulab.cmsservice.entity.CmsBanner;
import com.tjulab.cmsservice.service.CmsBannerService;
import com.tjulab.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 前台banner管理接口
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/cmsservice/cmsbannerfront")
@CrossOrigin
public class CmsBannerFrontController {

    @Autowired
    private CmsBannerService cmsBannerService;

    /**
     * 查询所有的banner
     * @return
     */
    @GetMapping("getAllBanner")
    public R getAllBanner() {
        List<CmsBanner> cmsBannerList = cmsBannerService.getAllBanner();
        return R.ok().data("cmsBannerList", cmsBannerList);
    }
}
