package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.dto.UserFriendDTO;
import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.entity.UserFriend;
import com.lty.www.intel_ta_dsp.mapper.UserFriendMapper;
import com.lty.www.intel_ta_dsp.service.UserFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserFriendServiceImpl implements UserFriendService {

    private final UserFriendMapper userFriendMapper;

    @Override
    public boolean addFriend(UserFriend userFriend) {
        return userFriendMapper.addFriend(userFriend);
    }

    @Override
    public boolean addFriend(UserFriendDTO dto) {
        UserFriend userFriend = UserFriend.builder()
                .userId(dto.getUserId())
                .friendId(dto.getFriendId())
                .build();
        return userFriendMapper.addFriend(userFriend);
    }

    @Override
    public List<User> getFriendList(Long userId) {
        return userFriendMapper.getFriendList(userId);
    }

}
