package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.dto.UserFriendDTO;
import com.lty.www.intel_ta_dsp.dto.UserFriendIdDTO;
import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.entity.UserFriend;
import com.lty.www.intel_ta_dsp.mapper.UserFriendMapper;
import com.lty.www.intel_ta_dsp.mapper.UserMapper;
import com.lty.www.intel_ta_dsp.service.UserFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class UserFriendServiceImpl implements UserFriendService {

    private final UserFriendMapper userFriendMapper;
    private final UserMapper userMapper;

    @Override
    public boolean addFriend(UserFriend userFriend) {
        return userFriendMapper.addFriend(userFriend);
    }

    @Override
    public boolean addFriend(UserFriendDTO dto) {
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId(dto.getUserId());
        User user = userMapper.findByUsername(dto.getFriendUsername());
        userFriend.setFriendId(user.getId());
        return userFriendMapper.addFriend(userFriend);
    }

    @Override
    public List<User> getFriendList(Long userId) {
        return userFriendMapper.getFriendList(userId);
    }

    @Override
    public boolean deleteFriend(UserFriend userFriend) {
        return userFriendMapper.deleteFriend(userFriend);
    }

    @Override
    public boolean deleteFriend(UserFriendIdDTO userFriendDTO) {
        UserFriend userFriend = UserFriend.builder()
                .userId(userFriendDTO.getUserId())
                .friendId(userFriendDTO.getFriendId())
                .build();
        return userFriendMapper.deleteFriend(userFriend);
    }

    @Override
    public boolean isFriend(UserFriend userFriend) {
        return userFriendMapper.isFriend(userFriend);
    }

}
