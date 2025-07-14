package com.lty.www.intel_ta_dsp;

import com.lty.www.intel_ta_dsp.dto.aidto.ScheduleGenerateDTO;
import com.lty.www.intel_ta_dsp.entity.*;
import com.lty.www.intel_ta_dsp.mapper.*;
import com.lty.www.intel_ta_dsp.service.FriendRequestService;
import com.lty.www.intel_ta_dsp.service.ScheduleService;
import com.lty.www.intel_ta_dsp.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static com.lty.www.intel_ta_dsp.entity.UserProfile.Gender.OTHER;

@SpringBootTest
class IntelTaDspApplicationTests {
    @Test
    void contextLoads() {
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;


    @Test
    void addUser(){
//        userService.addUser(User.builder()
//                .username("alice")
//                .password("123456")
//                .role("USER")
//                .build());
//
//        userService.addUser(User.builder()
//                .username("bob")
//                .password("123456")
//                .role("USER")
//                .build());
//
//        userService.addUser(User.builder()
//                .username("admin")
//                .password("admin123")
//                .role("ADMIN")
//                .build());
          userService.addUser(User.builder()
                .username("刘天野")
                .password("123")
                .role("ADMIN")
                .build());

    }
    @Autowired
    UserProfileMapper userProfileMapper;

    @Test
    void addProfile() {
        UserProfile newUserProfile = UserProfile.builder()
                .userId(26L)
                .nickname("你的昵称，可更改")
                .gender(OTHER)
                .avatarUrl("待上传")
                .birthday(null)
                .bio("这个人很懒，懒得介绍自己")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        userProfileMapper.insertUserProfile(newUserProfile);
    }

    @Autowired
    private ScheduleService scheduleService;

    @Test
    void testFindFromStartToEnd() {
        ScheduleGenerateDTO dto = new ScheduleGenerateDTO();
        dto.setUserId(26L);
        dto.setStartTime("2025-12-16 00:00:00");
        dto.setEndTime("2025-12-21 23:59:59");

        List<Schedule> schedules = scheduleService.findFromStartToEnd(dto);

        System.out.println("查询到的日程数量：" + schedules.size());
        for (Schedule s : schedules) {
            System.out.println(s);
        }

    }


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
        FriendRequest friendRequest = FriendRequest.builder()
                .senderId(5L)
                .receiverId(6L)
                .build();
        friendRequestService.sendAddFriendRequest(friendRequest);
    }

    @Autowired
    private UserDiaryMapper userDiaryMapper;

    @Test
    void testUserDiaryMapper() {
        System.out.println(userDiaryMapper.findAll());
    }


}
