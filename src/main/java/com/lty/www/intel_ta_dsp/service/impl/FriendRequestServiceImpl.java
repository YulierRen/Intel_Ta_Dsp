package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.dto.FriendRequestDTO;
import com.lty.www.intel_ta_dsp.entity.FriendRequest;
import com.lty.www.intel_ta_dsp.mapper.FriendRequestMapper;
import com.lty.www.intel_ta_dsp.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FriendRequestServiceImpl implements FriendRequestService {
    private final FriendRequestMapper friendRequestMapper;

    @Override
    public boolean sendAddFriendRequest(FriendRequest friendRequest) {
        return friendRequestMapper.addFriendRequest(friendRequest);
    }

    @Override
    public boolean sendAddFriendRequest(FriendRequestDTO dto) {
        FriendRequest friendRequest = FriendRequest.builder()
                .senderId(dto.getSenderId())
                .receiverId(dto.getReceiverId())
                .build();
        return friendRequestMapper.addFriendRequest(friendRequest);
    }
}
