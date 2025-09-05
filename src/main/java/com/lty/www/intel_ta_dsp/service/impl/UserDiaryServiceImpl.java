package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.datasource.Master;
import com.lty.www.intel_ta_dsp.datasource.Slave;
import com.lty.www.intel_ta_dsp.dto.UserDiaryDTO;
import com.lty.www.intel_ta_dsp.entity.UserDiary;
import com.lty.www.intel_ta_dsp.mapper.UserDiaryMapper;
import com.lty.www.intel_ta_dsp.service.UserDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserDiaryServiceImpl implements UserDiaryService {
    private final UserDiaryMapper diaryMapper;

    @Override
    @Slave
    @Cacheable(value = "userDiaries", key = "#userId")
    public List<UserDiary> getDiariesByUserId(Long userId) {
        return diaryMapper.findByUserId(userId);
    }

    @Override
    @Master
    public boolean updateIsPublic(Long id, Boolean isPublic) {
        return diaryMapper.updateIsPublic(id, isPublic) > 0;
    }

    @Override
    @Master
    public UserDiary createDiary(UserDiaryDTO dto) {
        UserDiary diary = new UserDiary();
        diary.setUserId(dto.getUserId());
        diary.setTitle(dto.getTitle());
        diary.setContent(dto.getContent());
        diary.setIsPublic(dto.getIsPublic());
        diary.setCreatedAt(LocalDateTime.now());
        diary.setUpdatedAt(LocalDateTime.now());

        diaryMapper.insert(diary);
        return diary;
    }

    @Override
    @Master
    public boolean deleteDiary(Long id) {
        return diaryMapper.deleteById(id) > 0;
    }

    @Override
    @Slave
    @Cacheable(value = "publicDiaries")
    public List<UserDiary> getPublicDiaries() {
        return diaryMapper.findAll().stream()
                .filter(diary -> Boolean.TRUE.equals(diary.getIsPublic()))
                .toList();
    }

    @Override
    @Master
    public boolean updateDiary(UserDiary userDiary) {
        return diaryMapper.updateDiary(userDiary);
    }

    @Override
    @Master
    public boolean updateDiary(UserDiaryDTO dto) {
        UserDiary diary = UserDiary.builder()
                .id(dto.getId())
               .userId(dto.getUserId())
               .title(dto.getTitle())
                .content(dto.getContent())
               .isPublic(dto.getIsPublic())
                .build();
        return diaryMapper.updateDiary(diary);
    }

    @Override
    @Slave
    @Cacheable(value = "userDiary", key = "#userId")
    public UserDiary getDiaryById(Long userId) {
        return diaryMapper.findById(userId);
    }
}