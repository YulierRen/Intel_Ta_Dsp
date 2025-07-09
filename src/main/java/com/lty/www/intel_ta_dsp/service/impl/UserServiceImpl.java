package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.mapper.UserMapper;
import com.lty.www.intel_ta_dsp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public boolean addUser(User user) {
        return userMapper.insertUser(user) > 0;
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
