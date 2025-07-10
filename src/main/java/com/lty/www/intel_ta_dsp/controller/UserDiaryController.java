package com.lty.www.intel_ta_dsp.controller;

import com.lty.www.intel_ta_dsp.dto.UserDiaryDTO;
import com.lty.www.intel_ta_dsp.entity.UserDiary;
import com.lty.www.intel_ta_dsp.service.UserDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userDiary")
@RequiredArgsConstructor
public class UserDiaryController {
    private final UserDiaryService userDiaryService;

    // 1. 返回指定userId的所有日记
    @PostMapping("/findAll")
    public ResponseEntity<?> findAll(@RequestParam Long userId) {
        List<UserDiary> diaries = userDiaryService.getDiariesByUserId(userId);
        return ResponseEntity.ok(diaries);
    }

    // 2. 根据id设置is_public属性
    @PostMapping("/setPublicStatus")
    public ResponseEntity<?> setPublicStatus(@RequestParam Long id, @RequestParam Boolean isPublic) {
        boolean success = userDiaryService.updateIsPublic(id, isPublic);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    // 3. 插入新日记
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDiaryDTO dto) {
        UserDiary diary = userDiaryService.createDiary(dto);
        return ResponseEntity.ok(diary);
    }

    // 4. 根据id删除日记
    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam Long id) {
        boolean success = userDiaryService.deleteDiary(id);
        return success ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    // 5. 扩展功能：查询公开日记列表
    @PostMapping("/findPublicDiaries")
    public ResponseEntity<?> findPublicDiaries() {
        List<UserDiary> publicDiaries = userDiaryService.getPublicDiaries();
        return ResponseEntity.ok(publicDiaries);
    }
}


