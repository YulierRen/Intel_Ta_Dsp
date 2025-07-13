package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.dto.FriendRequestDTO;
import com.lty.www.intel_ta_dsp.entity.FriendRequest;
import com.lty.www.intel_ta_dsp.mapper.FriendRequestMapper;
import com.lty.www.intel_ta_dsp.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<FriendRequest> findAllByReceiverId(Long receiverId) {
        return friendRequestMapper.findAllByReceiverId(receiverId);
    }

    @Override
    public boolean dropFriendRequest(FriendRequest friendRequest) {
        return friendRequestMapper.dropFriendRequest(friendRequest);
    }

    @Override
    public boolean dropFriendRequestById(Long id) {
        return friendRequestMapper.dropFriendRequestById(id);
    }
}
