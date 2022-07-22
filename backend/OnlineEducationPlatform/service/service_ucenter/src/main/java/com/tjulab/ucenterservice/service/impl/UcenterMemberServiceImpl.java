package com.tjulab.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.commonutils.jwt.JwtUtils;
import com.tjulab.commonutils.md5.MD5;
import com.tjulab.servicebase.exceptionhandler.MyException;
import com.tjulab.ucenterservice.entity.UcenterMember;
import com.tjulab.ucenterservice.mapper.UcenterMemberMapper;
import com.tjulab.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-22
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 用户登录
     * @param ucenterMember
     * @return
     */
    @Override
    public String login(UcenterMember ucenterMember) {
        String phoneNumber = ucenterMember.getMobile();
        String password = ucenterMember.getPassword();

        // 1. 判断手机号或者密码是否为空
        if (StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(password)) {
            throw new MyException(20001, "手机号或者登录密码为空，登录失败");
        }

        // 2. 判断手机号是否正确
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", phoneNumber);
        UcenterMember member = baseMapper.selectOne(queryWrapper);
        if (member == null) {
            throw new MyException(20001, "手机号错误，登录失败");
        }

        // 3. 判断密码是否正确
        String passwordByMD5 = MD5.encrypt(password);
        if(!passwordByMD5.equals(member.getPassword())) {
            throw new MyException(20001, "密码错误，登录失败");
        }

        // 4. 判断用户是否被禁用
        if(member.getIsDisabled()) {
            throw new MyException(20001, "该用户已被禁止登录，登录失败");
        }

        // 5. 登录成功，生成token字符串并返回
        String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
        return jwtToken;
    }

}
