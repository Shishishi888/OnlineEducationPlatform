package com.tjulab.ucenterservice.service;

import com.tjulab.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-22
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    /**
     * 用户登录
     * @param ucenterMember
     * @return
     */
    String login(UcenterMember ucenterMember);
}
