package com.lty.www.intel_ta_dsp.controller;


import com.lty.www.intel_ta_dsp.dto.FriendRequestDTO;
import com.lty.www.intel_ta_dsp.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/friendRequest")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody FriendRequestDTO dto) {
        if(friendRequestService.sendAddFriendRequest(dto)){
            return ResponseEntity.ok("发送成功");
        }
        else{
            return ResponseEntity.ok("发送失败");
        }
    }
}
