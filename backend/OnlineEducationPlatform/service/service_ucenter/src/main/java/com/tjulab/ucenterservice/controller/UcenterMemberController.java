package com.tjulab.ucenterservice.controller;


import com.tjulab.commonutils.R.R;
import com.tjulab.commonutils.jwt.JwtUtils;
import com.tjulab.commonvo.ordervo.UcenterMemberForOrder;
import com.tjulab.ucenterservice.entity.UcenterMember;
import com.tjulab.ucenterservice.entity.vo.RegisterVo;
import com.tjulab.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-22
 */
@RestController
@RequestMapping("/ucenterservice/user")
@CrossOrigin
public class UcenterMemberController {

    @Autowired
    private UcenterMemberService ucenterMemberService;

    /**
     * 用户登录
     * @param ucenterMember
     * @return
     */
    @PostMapping("login")
    public R loginUser(@RequestBody UcenterMember ucenterMember) {
        String token = ucenterMemberService.login(ucenterMember);
        return R.ok().data("token", token);
    }

    /**
     * 用户注册
     * @param registerVo
     * @return
     */
    @PostMapping("register")
    public R registerUser(@RequestBody RegisterVo registerVo) {
        ucenterMemberService.register(registerVo);
        return R.ok();
    }

    /**
     * 获取用户信息（根据token获取）
     * @param request
     * @return
     */
    @GetMapping("getUserInfo")
    public R getUserInfo(HttpServletRequest request) {
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        UcenterMember ucenterMember = ucenterMemberService.getById(memberId);
        return R.ok().data("userInfo", ucenterMember);
    }

    /**
     * 查询用户信息（根据用户ID查询）
     * @param userId
     * @return
     */
    @PostMapping("getUserInfoForOrder/{userId}")
    public UcenterMemberForOrder getUserInfoForOrder(@PathVariable String userId) {
        UcenterMemberForOrder ucenterMemberForOrder = new UcenterMemberForOrder();
        UcenterMember ucenterMember = ucenterMemberService.getById(userId);
        BeanUtils.copyProperties(ucenterMember, ucenterMemberForOrder);
        return ucenterMemberForOrder;
    }

}

