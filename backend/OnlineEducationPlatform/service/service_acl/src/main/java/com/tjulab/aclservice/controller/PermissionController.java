package com.tjulab.aclservice.controller;


import com.tjulab.aclservice.entity.Permission;
import com.tjulab.aclservice.service.PermissionService;
import com.tjulab.commonutils.R.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@RestController
@RequestMapping("/admin/acl/permission")
// @CrossOrigin
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    /**
     * 查询所有菜单
     * @return
     */
    @ApiOperation(value = "查询所有菜单")
    @GetMapping("queryAllMenu")
    public R queryAllMenu() {
        List<Permission> permissionList =  permissionService.queryAllMenu();
        return R.ok().data("permissionListList", permissionList);
    }

    /**
     * 删除菜单（根据菜单ID删除）
     * @param id
     * @return
     */
    @ApiOperation(value = "递归删除菜单")
    @DeleteMapping("removeMenuById/{id}")
    public R removeMenuById(@PathVariable String id) {
        permissionService.removeMenuById(id);
        return R.ok();
    }

    /**
     * 为角色分配菜单权限
     * @param roleId
     * @param permissionId
     * @return
     */
    @ApiOperation(value = "为角色分配菜单权限")
    @PostMapping("/doAssign")
    public R doAssign(String roleId,String[] permissionId) {
        permissionService.saveRolePermissionRealtionShip(roleId,permissionId);
        return R.ok();
    }

    @ApiOperation(value = "根据角色获取菜单")
    @GetMapping("toAssign/{roleId}")
    public R toAssign(@PathVariable String roleId) {
        List<Permission> list = permissionService.selectAllMenu(roleId);
        return R.ok().data("children", list);
    }

    @ApiOperation(value = "新增菜单")
    @PostMapping("save")
    public R save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return R.ok();
    }

    @ApiOperation(value = "修改菜单")
    @PutMapping("update")
    public R updateById(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return R.ok();
    }

}

