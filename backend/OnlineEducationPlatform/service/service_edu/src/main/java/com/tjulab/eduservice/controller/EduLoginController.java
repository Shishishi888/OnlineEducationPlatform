package com.tjulab.eduservice.controller;

import com.tjulab.commonutils.R.R;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eduservice/user")
// @CrossOrigin  // 解决跨域问题；如果使用Gateway网关解决跨域问题，则不能使用该注解
public class EduLoginController {

    // login
    @PostMapping("login")
    public R login() {
        return R.ok().data("token", "admin");
    }

    // info
    @GetMapping("info")
    public R info() {
        return R.ok().data("roles", "[admin]").data("name", "admin").data("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
    }
}
