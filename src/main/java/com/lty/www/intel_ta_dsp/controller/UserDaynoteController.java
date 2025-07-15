package com.lty.www.intel_ta_dsp.controller;


import com.lty.www.intel_ta_dsp.dto.UserDaynoteDTO;
import com.lty.www.intel_ta_dsp.entity.UserDaynote;
import com.lty.www.intel_ta_dsp.service.UserDaynoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/userdaynote")
@RequiredArgsConstructor
public class UserDaynoteController {

    private final UserDaynoteService userDaynoteService;

    @GetMapping
    public List<UserDaynote> selectBy(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        UserDaynoteDTO dto = UserDaynoteDTO.builder()
                .userId(userId)
                .date(date)
                .build();

        return userDaynoteService.selectBy(dto);
    }

    /**
     * 新增用户某天的日记
     */
    @PostMapping
    public boolean insert(@RequestBody UserDaynote userDaynote) {
        return userDaynoteService.insert(userDaynote);
    }

    /**
     * 修改用户某天的日记
     */
    @PutMapping
    public boolean update(@RequestBody UserDaynote userDaynote) {
        return userDaynoteService.update(userDaynote);
    }

    /**
     * 删除用户某天的日记
     */
    @DeleteMapping
    public boolean delete(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date) {

        UserDaynoteDTO dto = UserDaynoteDTO.builder()
                .userId(userId)
                .date(date)
                .build();

        return userDaynoteService.delete(dto);
    }
}
