package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.dto.FriendRequestDTO;
import com.lty.www.intel_ta_dsp.entity.FriendRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FriendRequestService {
    boolean sendAddFriendRequest(FriendRequest friendRequest);

    boolean sendAddFriendRequest(FriendRequestDTO dto);

    List<FriendRequest> findAllByReceiverId(@Param("receiverId") Long receiverId);

    boolean dropFriendRequest(FriendRequest friendRequest);

    boolean dropFriendRequestById(Long id);
}
