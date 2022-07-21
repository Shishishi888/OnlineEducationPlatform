package com.tjulab.cmsservice.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tjulab.cmsservice.entity.CmsBanner;
import com.tjulab.cmsservice.service.CmsBannerService;
import com.tjulab.commonutils.R.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 后台banner管理接口
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-19
 */
@RestController
@RequestMapping("/cmsservice/cmsbanneradmin")
@CrossOrigin
public class CmsBannerAdminController {

    @Autowired
    private CmsBannerService cmsBannerService;

    /**
     * 查询banner（根据banner的ID查询）
     * @param id
     * @return
     */
    @GetMapping("getBanner/{id}")
    public R getBanner(@PathVariable String id) {
        CmsBanner cmsBanner = cmsBannerService.getById(id);
        return R.ok().data("cmsBanner", cmsBanner);
    }

    /**
     * 查询banner（分页查询）
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("pageBanner/{page}/{limit}")
    public R pageBanner(@PathVariable long page, @PathVariable long limit) {
        Page<CmsBanner> cmsBannerPage = new Page<>(page, limit);
        cmsBannerService.page(cmsBannerPage, null);
        return R.ok().data("items", cmsBannerPage.getRecords()).data("total", cmsBannerPage.getTotal());
    }

    /**
     * 添加banner
     * @param cmsBanner
     * @return
     */
    @PostMapping("addBanner")
    public R addBanner(@RequestBody CmsBanner cmsBanner) {
        cmsBannerService.save(cmsBanner);
        return R.ok();
    }

    /**
     * 修改banner
     * @param cmsBanner
     * @return
     */
    @PostMapping("updateBanner")
    public R updateBanner(@RequestBody CmsBanner cmsBanner) {
        cmsBannerService.updateById(cmsBanner);
        return R.ok();
    }

    /**
     * 删除banner（根据banner的ID删除）
     * @param id
     * @return
     */
    @DeleteMapping("deleteBanner/{id}")
    public R deleteBanner(@PathVariable String id) {
        cmsBannerService.removeById(id);
        return R.ok();
    }

}

