package com.tjulab.aclservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.tjulab.aclservice.entity.Permission;
import com.tjulab.aclservice.entity.RolePermission;
import com.tjulab.aclservice.entity.User;
import com.tjulab.aclservice.helper.MemuHelper;
import com.tjulab.aclservice.helper.PermissionHelper;
import com.tjulab.aclservice.mapper.PermissionMapper;
import com.tjulab.aclservice.service.PermissionService;
import com.tjulab.aclservice.service.RolePermissionService;
import com.tjulab.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2020-01-12
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Autowired
    private RolePermissionService rolePermissionService;
    
    @Autowired
    private UserService userService;

    /**
     * 查询所有菜单
     * @return
     */
    @Override
    public List<Permission> queryAllMenu() {

        // 1. 查询菜单表中的数据
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("id");
        List<Permission> permissionList = baseMapper.selectList(queryWrapper);

        // 2. 将菜单集合按照要求进行封装
        List<Permission> finalPermissionList = build(permissionList);

        return finalPermissionList;
    }

    //根据角色获取菜单
    @Override
    public List<Permission> selectAllMenu(String roleId) {
        List<Permission> allPermissionList = baseMapper.selectList(new QueryWrapper<Permission>().orderByAsc("CAST(id AS SIGNED)"));

        //根据角色id获取角色权限
        List<RolePermission> rolePermissionList = rolePermissionService.list(new QueryWrapper<RolePermission>().eq("role_id",roleId));
        //转换给角色id与角色权限对应Map对象
//        List<String> permissionIdList = rolePermissionList.stream().map(e -> e.getPermissionId()).collect(Collectors.toList());
//        allPermissionList.forEach(permission -> {
//            if(permissionIdList.contains(permission.getId())) {
//                permission.setSelect(true);
//            } else {
//                permission.setSelect(false);
//            }
//        });
        for (int i = 0; i < allPermissionList.size(); i++) {
            Permission permission = allPermissionList.get(i);
            for (int m = 0; m < rolePermissionList.size(); m++) {
                RolePermission rolePermission = rolePermissionList.get(m);
                if(rolePermission.getPermissionId().equals(permission.getId())) {
                    permission.setSelect(true);
                }
            }
        }


        List<Permission> permissionList = build(allPermissionList);
        return permissionList;
    }

    /**
     * 为角色分配菜单权限
     * @param roleId
     * @param permissionIds
     */
    @Override
    public void saveRolePermissionRealtionShip(String roleId, String[] permissionIds) {
        List<RolePermission> rolePermissionList = new ArrayList<>();
        for(String permissionId : permissionIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermissionId(permissionId);
            rolePermissionList.add(rolePermission);
        }
        rolePermissionService.saveBatch(rolePermissionList);
    }

    /**
     * 删除菜单（根据菜单ID删除）
     * @param id
     */
    @Override
    public void removeMenuById(String id) {
        // 创建idList集合，用于封装要删除的菜单项的菜单id
        List<String> idList = new ArrayList<>();

        // 像idList中递归添加要删除的菜单项的菜单id
        this.selectChildListById(id, idList);

        idList.add(id);
        baseMapper.deleteBatchIds(idList);
    }

    //根据用户id获取用户菜单
    @Override
    public List<String> selectPermissionValueByUserId(String id) {

        List<String> selectPermissionValueList = null;
        if(this.isSysAdmin(id)) {
            //如果是系统管理员，获取所有权限
            selectPermissionValueList = baseMapper.selectAllPermissionValue();
        } else {
            selectPermissionValueList = baseMapper.selectPermissionValueByUserId(id);
        }
        return selectPermissionValueList;
    }

    @Override
    public List<JSONObject> selectPermissionByUserId(String userId) {
        List<Permission> selectPermissionList = null;
        if(this.isSysAdmin(userId)) {
            //如果是超级管理员，获取所有菜单
            selectPermissionList = baseMapper.selectList(null);
        } else {
            selectPermissionList = baseMapper.selectPermissionByUserId(userId);
        }

        List<Permission> permissionList = PermissionHelper.bulid(selectPermissionList);
        List<JSONObject> result = MemuHelper.bulid(permissionList);
        return result;
    }

    /**
     * 判断用户是否系统管理员
     * @param userId
     * @return
     */
    private boolean isSysAdmin(String userId) {
        User user = userService.getById(userId);

        if(null != user && "admin".equals(user.getUsername())) {
            return true;
        }
        return false;
    }

    /**
     *	递归获取子节点
     * @param id
     * @param idList
     */
    private void selectChildListById(String id, List<String> idList) {
        // 查询当前菜单项的所有子菜单
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("pid", id);
        queryWrapper.select("id");
        List<Permission> childList = baseMapper.selectList(queryWrapper);

        // 递归查询每个子菜单项
        childList.stream().forEach(item -> {
            idList.add(item.getId());
            this.selectChildListById(item.getId(), idList);
        });
    }

    /**
     * 封装菜单集合
     * @param treeNodes
     * @return
     */
    private static List<Permission> build(List<Permission> treeNodes) {
        List<Permission> permissionList = new ArrayList<>();
        // 遍历菜单list集合
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {  // 找到顶层菜单项
                treeNode.setLevel(1);  // 将该菜单项的level值设置为1
                permissionList.add(findChildren(treeNode,treeNodes));  // 递归查询顶层菜单项的子菜单，并封装到finalNode集合中
            }
        }
        return permissionList;
    }

    /**
     * 递归查找菜单项的子菜单
     * @param treeNodes
     * @return
     */
    private static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        treeNode.setChildren(new ArrayList<Permission>());

        for (Permission it : treeNodes) {
            if(treeNode.getId().equals(it.getPid())) {
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.getChildren().add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}
