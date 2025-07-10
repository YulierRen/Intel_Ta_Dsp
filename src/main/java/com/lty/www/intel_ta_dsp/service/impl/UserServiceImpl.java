package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.mapper.UserMapper;
import com.lty.www.intel_ta_dsp.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;  // 用于密码加密

    @Override
    public List<User> findAll() {
        return userMapper.findAll();  // 查询所有用户
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);  // 根据用户名查找用户
    }

    @Override
    public boolean addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 密码加密
        return userMapper.insertUser(user) > 0;  // 添加新用户
    }

    @Override
    public boolean changePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 密码加密
        return userMapper.changePassword(user);  // 修改用户密码
    }

    @Override
    public boolean changeUsername(User user) {
        return userMapper.changeUsername(user);  // 修改用户名
    }

    @Override
    public boolean changeRole(User user) {
        return userMapper.changeRole(user);  // 修改用户角色
    }


    @Override
    public boolean deleteUser(User user) {
        return userMapper.dropUser(user);  // 根据用户对象删除用户
    }

    @Override
    public boolean deleteUserById(Long userId) {
        return userMapper.dropUser(userId);  // 根据用户ID删除用户
    }
}
