package com.lty.www.intel_ta_dsp.service.impl;

import com.lty.www.intel_ta_dsp.dto.UserDiaryDTO;
import com.lty.www.intel_ta_dsp.entity.UserDiary;
import com.lty.www.intel_ta_dsp.mapper.UserDiaryMapper;
import com.lty.www.intel_ta_dsp.service.UserDiaryService;
import lombok.RequiredArgsConstructor;
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
    public List<UserDiary> getDiariesByUserId(Long userId) {
        return diaryMapper.findByUserId(userId);
    }

    @Override
    public boolean updateIsPublic(Long id, Boolean isPublic) {
        return diaryMapper.updateIsPublic(id, isPublic) > 0;
    }

    @Override
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
    public boolean deleteDiary(Long id) {
        return diaryMapper.deleteById(id) > 0;
    }

    @Override
    public List<UserDiary> getPublicDiaries() {
        return diaryMapper.findAll().stream()
                .filter(diary -> Boolean.TRUE.equals(diary.getIsPublic()))
                .toList();
    }

    @Override
    public boolean updateDiary(UserDiary userDiary) {
        return diaryMapper.updateDiary(userDiary);
    }

    @Override
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
    public UserDiary getDiaryById(Long userId) {
        return diaryMapper.findById(userId);
    }
}