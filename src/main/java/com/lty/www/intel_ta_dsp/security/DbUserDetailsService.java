package com.lty.www.intel_ta_dsp.security;

import com.lty.www.intel_ta_dsp.mapper.UserMapper;
import com.lty.www.intel_ta_dsp.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class DbUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User dbUser = userMapper.findByUsername(username);
        if (dbUser == null) {
            throw new UsernameNotFoundException("用户不存在：" + username);
        }
        return new CustomUserDetails(dbUser);
    }
}
