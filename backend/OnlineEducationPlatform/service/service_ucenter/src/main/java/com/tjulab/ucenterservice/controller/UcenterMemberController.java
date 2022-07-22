package com.tjulab.ucenterservice.controller;


import com.tjulab.commonutils.R.R;
import com.tjulab.ucenterservice.entity.UcenterMember;
import com.tjulab.ucenterservice.service.UcenterMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author ShiYang
 * @since 2022-07-22
 */
@RestController
@RequestMapping("/ucenterservice/member")
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

}

