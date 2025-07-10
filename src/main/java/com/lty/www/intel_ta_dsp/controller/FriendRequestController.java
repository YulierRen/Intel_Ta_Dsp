package com.lty.www.intel_ta_dsp.controller;

import com.lty.www.intel_ta_dsp.dto.FriendRequestDTO;
import com.lty.www.intel_ta_dsp.entity.FriendRequest;
import com.lty.www.intel_ta_dsp.service.FriendRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/friendRequest")
@RequiredArgsConstructor
public class FriendRequestController {

    private final FriendRequestService friendRequestService;

    /**
     * 发送好友请求
     */
    @PostMapping("/send")
    public ResponseEntity<?> send(@RequestBody FriendRequestDTO dto) {
        if (friendRequestService.sendAddFriendRequest(dto)) {
            return ResponseEntity.ok("发送成功");
        } else {
            return ResponseEntity.ok("发送失败");
        }
    }

    /**
     * 获取指定用户收到的所有好友请求
     */
    @GetMapping("/received/{receiverId}")
    public ResponseEntity<List<FriendRequest>> getReceivedRequests(@PathVariable Long receiverId) {
        List<FriendRequest> requests = friendRequestService.findAllByReceiverId(receiverId);
        return ResponseEntity.ok(requests);
    }

    /**
     * 删除好友请求（例如拒绝或撤回）
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteRequest(@RequestBody FriendRequest request) {
        if (friendRequestService.dropFriendRequest(request)) {
            return ResponseEntity.ok("删除成功");
        } else {
            return ResponseEntity.ok("删除失败");
        }
    }
}
