package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.entity.UserProfile;
import com.lty.www.intel_ta_dsp.mapper.UserProfileMapper;
import com.lty.www.intel_ta_dsp.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserProfileMapper userProfileMapper;

    // 确保方法签名与接口完全一致
    @Override
    public boolean insertUserProfile(UserProfile userProfile) {
        userProfile.setCreatedAt(LocalDateTime.now());
        userProfile.setUpdatedAt(LocalDateTime.now());

        return userProfileMapper.insertUserProfile(userProfile)>0;
    }


    @Override
    public UserProfile getUserProfileByUserId(Long userId) {
        return userProfileMapper.selectByUserId(userId);
    }

    @Override
    public boolean updateUserProfile(UserProfile userProfile) {
        userProfile.setUpdatedAt(LocalDateTime.now());
        return userProfileMapper.updateByUserIdSelective(userProfile)>0;
    }

    @Override
    public boolean deleteUserProfileByUserId(Long userId) {
        return userProfileMapper.deleteByUserId(userId);
    }
}