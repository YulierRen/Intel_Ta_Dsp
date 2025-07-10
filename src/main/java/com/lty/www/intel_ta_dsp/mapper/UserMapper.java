package com.lty.www.intel_ta_dsp.mapper;

import com.lty.www.intel_ta_dsp.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {
    User findByUsername(String username);

    int insertUser(User user);

    boolean changePassword(User user);

    boolean changeUsername(User user);

    boolean changeRole(User user);

    boolean dropUser(User user);

    boolean dropUser(Long userId);

    List<User> findAll();
}
