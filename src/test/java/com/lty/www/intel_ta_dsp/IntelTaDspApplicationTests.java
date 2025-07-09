package com.lty.www.intel_ta_dsp;

import com.lty.www.intel_ta_dsp.entity.FriendRequest;
import com.lty.www.intel_ta_dsp.entity.UserFriend;
import com.lty.www.intel_ta_dsp.mapper.UserFriendMapper;
import com.lty.www.intel_ta_dsp.mapper.UserMapper;
import com.lty.www.intel_ta_dsp.service.FriendRequestService;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IntelTaDspApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ScheduleService scheduleService;
    @Test
    void testUserMapper() {
        System.out.println(userMapper.findByUsername("user"));
    }

    @Test
    void testScheduleMapper() {
        System.out.println(scheduleService.getScheduleByStudentId(5L));
    }


    @Autowired
    private UserFriendMapper userFriendMapper;
    @Test
    void testUserFriendMapper() {
        UserFriend userFriend = new UserFriend();
        userFriend.setUserId(5L);
        userFriend.setFriendId(6L);
        userFriendMapper.addFriend(userFriend);
    }
    @Test
    void testUserMapperFriendList(){
        System.out.println(userFriendMapper.getFriendList(5L));
        System.out.println(userFriendMapper.getFriendList(6L));
    }

    @Autowired
    private FriendRequestService friendRequestService;
    @Test
    void testFriendRequestMapper() {
        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(5L);
        friendRequest.setReceiverId(6L);
        friendRequestService.sendAddFriendRequest(friendRequest);
    }

}
