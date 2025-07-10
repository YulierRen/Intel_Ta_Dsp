package com.lty.www.intel_ta_dsp.controller;

import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 查询所有用户
     */
    @PostMapping("/allUser")
    public ResponseEntity<List<User>> allUser() {
        List<User> users = userService.findAll();
        return ResponseEntity.ok(users);
    }

    /**
     * 通过用户名查询用户
     */
    @GetMapping("/find")
    public ResponseEntity<User> findByUsername(@RequestParam String username) {
        User user = userService.findByUsername(username);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * 添加新用户
     */
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody User user) {
        if (userService.addUser(user)) {
            return ResponseEntity.ok("添加成功");
        } else {
            return ResponseEntity.badRequest().body("添加失败");
        }
    }

    /**
     * 修改密码
     */
    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody User user) {
        if (userService.changePassword(user)) {
            return ResponseEntity.ok("修改密码成功");
        } else {
            return ResponseEntity.badRequest().body("修改密码失败");
        }
    }

    /**
     * 修改用户名
     */
    @PutMapping("/changeUsername")
    public ResponseEntity<String> changeUsername(@RequestBody User user) {
        if (userService.changeUsername(user)) {
            return ResponseEntity.ok("修改用户名成功");
        } else {
            return ResponseEntity.badRequest().body("修改用户名失败");
        }
    }

    /**
     * 修改角色
     */
    @PutMapping("/changeRole")
    public ResponseEntity<String> changeRole(@RequestBody User user) {
        if (userService.changeRole(user)) {
            return ResponseEntity.ok("修改角色成功");
        } else {
            return ResponseEntity.badRequest().body("修改角色失败");
        }
    }

    /**
     * 删除用户（传完整对象）
     */
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@RequestBody User user) {
        if (userService.deleteUser(user)) {
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.badRequest().body("删除失败");
        }
    }

    /**
     * 根据ID删除用户
     */
    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable("id") Long id) {
        if (userService.deleteUserById(id)) {
            return ResponseEntity.ok("根据ID删除成功");
        } else {
            return ResponseEntity.badRequest().body("根据ID删除失败");
        }
    }
}
