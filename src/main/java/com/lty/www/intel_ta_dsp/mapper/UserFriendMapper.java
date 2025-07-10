package com.lty.www.intel_ta_dsp.mapper;


import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.entity.UserFriend;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserFriendMapper {
    boolean addFriend(UserFriend  userFriend) ;

    List<User> getFriendList(Long userId) ;

    boolean deleteFriend(UserFriend userFriend) ;
}
