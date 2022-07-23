package com.tjulab.ucenterservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tjulab.commonutils.jwt.JwtUtils;
import com.tjulab.commonutils.MD5.MD5;
import com.tjulab.servicebase.exceptionhandler.MyException;
import com.tjulab.ucenterservice.entity.UcenterMember;
import com.tjulab.ucenterservice.entity.vo.RegisterVo;
import com.tjulab.ucenterservice.mapper.UcenterMemberMapper;
import com.tjulab.ucenterservice.service.UcenterMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
        String passwordEncryptedByMD5 = MD5.encrypt(password);
        if(!passwordEncryptedByMD5.equals(member.getPassword())) {
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

    /**
     * 用户注册
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {
        String nickName = registerVo.getNickname();    // 用户昵称
        String phoneNumber = registerVo.getMobile();   // 手机号
        String passsword = registerVo.getPassword();;  // 用户密码
        String code = registerVo.getCode();            // 手机验证码

        // 1. 注册信息非空判断
        if(StringUtils.isEmpty(nickName) || StringUtils.isEmpty(phoneNumber) || StringUtils.isEmpty(passsword) || StringUtils.isEmpty(code)) {
            throw new MyException(20001, "缺少关键的注册信息，注册失败");
        }

        // 2. 手机验证码判断
        String codeFromRedis = redisTemplate.opsForValue().get(phoneNumber);
        if(!code.equals(codeFromRedis)) {
            throw new MyException(20001, "验证码错误或者失效，注册失败");
        }

        // 3. 手机号重复判断
        QueryWrapper<UcenterMember> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", phoneNumber);
        Integer count = baseMapper.selectCount(queryWrapper);
        if(count > 0) {
            throw new MyException(20001, "该手机号已被注册，注册失败");
        }

        UcenterMember member = new UcenterMember();
        member.setMobile(phoneNumber);
        member.setNickname(nickName);
        member.setPassword(MD5.encrypt(passsword));
        member.setIsDisabled(false);
        member.setAvatar("https://tjulab-online-education-platform.oss-cn-beijing.aliyuncs.com/default_teacher_avatar.png");
        baseMapper.insert(member);
    }

}
