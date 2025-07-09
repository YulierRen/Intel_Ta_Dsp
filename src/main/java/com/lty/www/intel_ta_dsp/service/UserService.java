package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    boolean addUser(User user);

    User findByUsername(String username);
}
