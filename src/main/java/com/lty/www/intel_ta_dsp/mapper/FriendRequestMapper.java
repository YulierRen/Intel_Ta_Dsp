package com.lty.www.intel_ta_dsp.mapper;

import com.lty.www.intel_ta_dsp.entity.FriendRequest;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestBody;

@Mapper
public interface FriendRequestMapper {
    boolean addFriendRequest(FriendRequest friendRequest);
}
