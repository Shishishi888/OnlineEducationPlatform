package com.tjulab.ucenterservice.service;

import com.tjulab.ucenterservice.entity.UcenterMember;
import com.baomidou.mybatisplus.extension.service.IService;
import com.tjulab.ucenterservice.entity.vo.RegisterVo;

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

    /**
     * 用户注册
     * @param registerVo
     */
    void register(RegisterVo registerVo);

    /**
     * 根据openId查询用户
     * @return
     * @param openId
     */
    UcenterMember getMemBerByOpenId(String openId);

    /**
     * 查询某一天的注册人数
     * @param date
     * @return
     */
    Integer countRegisterUser(String date);
}
