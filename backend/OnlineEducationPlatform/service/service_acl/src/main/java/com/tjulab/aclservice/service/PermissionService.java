package com.tjulab.aclservice.service;

import com.alibaba.fastjson.JSONObject;
import com.tjulab.aclservice.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 查询所有菜单
     * @return
     */
    List<Permission> queryAllMenu();

    //根据角色获取菜单
    List<Permission> selectAllMenu(String roleId);

    /**
     * 为角色分配菜单权限
     * @param roleId
     * @param permissionId
     */
    void saveRolePermissionRealtionShip(String roleId, String[] permissionId);

    /**
     * 删除菜单（根据菜单ID删除）
     * @param id
     */
    void removeMenuById(String id);

    //根据用户id获取用户菜单
    List<String> selectPermissionValueByUserId(String id);

    List<JSONObject> selectPermissionByUserId(String id);

}