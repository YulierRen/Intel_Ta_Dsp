package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.dto.FriendRequestDTO;
import com.lty.www.intel_ta_dsp.entity.FriendRequest;

public interface FriendRequestService {
    boolean sendAddFriendRequest(FriendRequest friendRequest);
    boolean sendAddFriendRequest(FriendRequestDTO dto);

}
