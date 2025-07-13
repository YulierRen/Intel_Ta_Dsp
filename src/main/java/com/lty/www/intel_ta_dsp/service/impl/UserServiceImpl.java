package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.entity.UserFriend;
import com.lty.www.intel_ta_dsp.entity.UserProfile;
import com.lty.www.intel_ta_dsp.mapper.*;
import com.lty.www.intel_ta_dsp.service.UserService;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lty.www.intel_ta_dsp.entity.UserProfile.Gender.OTHER;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserProfileMapper userProfileMapper;
    private final UserFriendMapper userFriendMapper;
    private final UserDiaryMapper diaryMapper;
    private final FriendRequestMapper friendRequestMapper;
    private final ScheduleMapper scheduleMapper;

    private final PasswordEncoder passwordEncoder;  // 用于密码加密

    @Override
    public List<User> findAll() {
        return userMapper.findAll();  // 查询所有用户
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);  // 根据用户名查找用户
    }

    @Override
    public boolean addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 密码加密
        boolean flag = userMapper.insertUser(user)>0;
        UserProfile newUserProfile = UserProfile.builder()
                        .userId(user.getId())
                        .nickname("你的昵称，可更改")
                        .gender(OTHER)
                        .avatarUrl("待上传")
                        .birthday(null)
                        .bio("这个人很懒，懒得介绍自己")
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build();
        userProfileMapper.insertUserProfile(newUserProfile);
        return flag;  // 添加新用户
    }

    @Override
    public boolean changePassword(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // 密码加密
        return userMapper.changePassword(user);  // 修改用户密码
    }

    @Override
    public boolean changeUsername(User user) {
        return userMapper.changeUsername(user);  // 修改用户名
    }

    @Override
    public boolean changeRole(User user) {
        return userMapper.changeRole(user);  // 修改用户角色
    }


    @Override
    public boolean deleteUser(User user) {
        List<User> friends=userFriendMapper.getFriendList(user.getId());
        List<UserFriend> friendMap = friends.stream().map(f -> UserFriend.builder()
                .userId(user.getId())
                .friendId(f.getId())
                .build()).toList();
        friendMap.forEach(userFriendMapper::deleteFriend);//删除friend相关的用户
        diaryMapper.deleteById(user.getId());//删除用户日记
        scheduleMapper.deleteSchedule(user.getId());//删除用户日程
        friendRequestMapper.dropFriendRequestById(user.getId());//删除用户好友请求列表
        userProfileMapper.deleteByUserId(user.getId());//删除用户个人资料
        return userMapper.dropUser(user);
    }

    @Override
    public boolean deleteUserById(Long userId) {
        return userMapper.dropUser(userId);  // 根据用户ID删除用户
    }
}
