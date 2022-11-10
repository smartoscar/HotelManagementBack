package com.oscar.springbootstudy.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.oscar.springbootstudy.commons.ApiData;
import com.oscar.springbootstudy.pojo.User;
import com.oscar.springbootstudy.pojo.dto.UserDto;
import com.oscar.springbootstudy.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/Admin")
public class UserController {

    @Autowired
    private UserService userService;

    // 登陆
    @GetMapping(value = "/login")
    public ApiData signIn(String username, String password, boolean rememberMe, HttpServletResponse response) throws InterruptedException {
        System.out.println("signIn: " + username + "," + password);
        ApiData apiData = userService.login(username, password, rememberMe);
        if (ApiData.STATUS_SUCCESS.equals(apiData.getCode())) {
            response.setHeader("token", apiData.getData().toString());
        }
        return apiData;
    }

    // 注册
    @PostMapping(value = "/signUp")
    public ApiData signUp(@RequestBody User user) throws InterruptedException {
        System.out.println("signUp: " + user);
        ApiData apiData;
        if (true) {
            apiData = new ApiData(ApiData.STATUS_SUCCESS, "注册成功");
        } else {
            apiData = new ApiData(ApiData.STATUS_FAIL, "注册失败");
        }
        return apiData;
    }

    // 查询
    @GetMapping("/list")
    @RequiresPermissions("admin:list")
    public ApiData list() {
        return new ApiData(ApiData.STATUS_SUCCESS, "查询成功", userService.list());
    }

    // 条件分页查询
    @GetMapping("/page")
    @RequiresPermissions("admin:list")
    public ApiData page(UserDto userDto) {
        IPage<UserDto> userDtoIPage = userService.pageUsers(userDto);
        return new ApiData(ApiData.STATUS_SUCCESS, "查询成功", userDtoIPage);
    }

    // 增加
    @PostMapping("/add")
    @RequiresPermissions("admin:add")
    public ApiData add(@RequestBody UserDto dto) {
        return userService.addUser(dto);
    }

    // 修改
    @PostMapping("/edit")
    @RequiresPermissions("admin:edit")
    public ApiData edit(@RequestBody UserDto userDto) {
        return userService.editUser(userDto);
    }

    // 删除
    @PostMapping("/delete/{id}")
    @RequiresPermissions("admin:delete")
    public ApiData delete(@PathVariable Integer id) {
        if (userService.deleteUserById(id)) {
            return new ApiData(ApiData.STATUS_SUCCESS, "删除成功");
        } else {
            return new ApiData(ApiData.STATUS_FAIL, "删除失败");
        }
    }

    // 个人中心
    @GetMapping("/getUser")
    public ApiData getUser(@RequestParam String username) {
        // TODO 需要把角色也查出来
        UserDto userDto = userService.getUserDtoByName(username);
        return new ApiData(ApiData.STATUS_SUCCESS, "查询成功", userDto);
    }

    // 修改密码
    @PostMapping("/resetPassword")
    public ApiData resetPassword(@RequestBody Map<String, String> params) {
        return userService.resetPassword(params.get("oldPassword"), params.get("newPassword"));
    }
}
