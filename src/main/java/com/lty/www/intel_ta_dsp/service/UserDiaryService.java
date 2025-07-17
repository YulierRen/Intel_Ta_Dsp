package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.dto.UserDiaryDTO;
import com.lty.www.intel_ta_dsp.entity.UserDiary;

import java.util.List;

public interface UserDiaryService {
    // 1. 返回指定userId的所有日记
    List<UserDiary> getDiariesByUserId(Long userId);

    // 2. 根据id设置is_public属性
    boolean updateIsPublic(Long id, Boolean isPublic);

    // 3. 插入新日记
    UserDiary createDiary(UserDiaryDTO dto);

    // 4. 根据id删除日记
    boolean deleteDiary(Long id);

    // 5. 扩展功能：查询公开日记
    List<UserDiary> getPublicDiaries();

    //6. 改内容
    boolean updateDiary(UserDiary userDiary);
    boolean updateDiary(UserDiaryDTO dto);

    UserDiary getDiaryById(Long userId);

}