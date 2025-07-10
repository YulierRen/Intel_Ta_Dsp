package com.lty.www.intel_ta_dsp.mapper;


import com.lty.www.intel_ta_dsp.entity.UserProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper

public interface UserProfileMapper {
    // 新增用户档案
    int insertUserProfile(UserProfile userProfile);

    // 根据用户ID查询档案
    UserProfile selectByUserId(@Param("userId") Long userId);

    // 用户更新档案（只更新非空字段）
    int updateByUserIdSelective(UserProfile userProfile);

}
//先写mapper.xml
