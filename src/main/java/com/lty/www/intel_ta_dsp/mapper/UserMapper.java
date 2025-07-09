package com.lty.www.intel_ta_dsp.mapper;

import com.lty.www.intel_ta_dsp.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User findByUsername(String username);
    int insertUser(User user);
}
