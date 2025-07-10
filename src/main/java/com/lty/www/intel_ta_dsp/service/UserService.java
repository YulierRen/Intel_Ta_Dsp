package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.entity.User;

import java.util.List;

public interface UserService {
    List<User> findAll();

    User findByUsername(String username);

    boolean addUser(User user);

    boolean changePassword(User user);

    boolean changeUsername(User user);

    boolean changeRole(User user);

    boolean deleteUser(User user);

    boolean deleteUserById(Long userId);
}

