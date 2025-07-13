package com.lty.www.intel_ta_dsp.mapper;

import com.lty.www.intel_ta_dsp.entity.FriendRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Mapper
public interface FriendRequestMapper {
    boolean addFriendRequest(FriendRequest friendRequest);

    List<FriendRequest> findAllByReceiverId(@Param("receiverId") Long receiverId);

    boolean dropFriendRequest(FriendRequest friendRequest);

    boolean dropFriendRequestById(Long id);
}
