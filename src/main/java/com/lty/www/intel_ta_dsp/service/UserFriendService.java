package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.dto.UserFriendDTO;
import com.lty.www.intel_ta_dsp.dto.UserFriendIdDTO;
import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.entity.UserFriend;

import java.util.List;

public interface UserFriendService {
    boolean addFriend(UserFriend userFriend) ;

    boolean addFriend(UserFriendDTO dto);

    List<User> getFriendList(Long userId);

    boolean deleteFriend(UserFriend userFriend);


    boolean deleteFriend(UserFriendIdDTO userFriendDTO);

    boolean isFriend(UserFriend userFriend);
}
