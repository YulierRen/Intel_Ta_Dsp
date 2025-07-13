package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.entity.UserProfile;

public interface UserProfileService {
    // 新增用户档案
    // 这里要改序列化的问题
    boolean insertUserProfile(UserProfile userProfile);

    // 根据用户ID查询档案
    UserProfile getUserProfileByUserId(Long userId);

    // 根据用户ID更新档案
    boolean updateUserProfile(UserProfile userProfile);

    boolean deleteUserProfileByUserId(Long userId);

}
//先写mapper.xml