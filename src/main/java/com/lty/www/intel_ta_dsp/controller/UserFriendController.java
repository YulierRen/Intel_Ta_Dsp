package com.lty.www.intel_ta_dsp.controller;


import com.lty.www.intel_ta_dsp.dto.UserFriendDTO;
import com.lty.www.intel_ta_dsp.dto.UserFriendIdDTO;
import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.entity.UserFriend;
import com.lty.www.intel_ta_dsp.mapper.UserFriendMapper;
import com.lty.www.intel_ta_dsp.mapper.UserMapper;
import com.lty.www.intel_ta_dsp.service.UserFriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/userFriend")
@RequiredArgsConstructor
public class UserFriendController {

    private final UserFriendService userFriendService;
    private UserMapper userMapper;

    @PostMapping("/getFriendList")
    public ResponseEntity<List<User>> getFriendList(@RequestParam Long id){
        List<User> friendList = userFriendService.getFriendList(id);
        return  ResponseEntity.ok(friendList);
    }

    @PostMapping("/addFriend")
    public ResponseEntity<?> addFriend(@RequestBody UserFriendDTO dto) {

        if(userFriendService.addFriend(dto)){
            return ResponseEntity.ok("成功");
        }
        else{
            return ResponseEntity.ok("添加失败");
        }
    }
    @PutMapping("/addFriendById")
    public ResponseEntity<?> addFriend(@RequestBody UserFriend userFriend) {
        if(userFriendService.addFriend(userFriend)){
            return ResponseEntity.ok("成功");
        }
        else{
            return ResponseEntity.ok("添加失败");
        }
    }
    @PostMapping("/deleteFriend")
    public ResponseEntity<?> deleteFriend(@RequestBody UserFriendIdDTO dto) {
        if(userFriendService.deleteFriend(dto)){
            return ResponseEntity.ok("删除成功");
        }
        else{
            return ResponseEntity.ok("删除失败");
        }
    }
    @DeleteMapping("/deleteFriendById")
    public ResponseEntity<?> deleteFriendById(@RequestBody UserFriend userFriend) {
        if(userFriendService.deleteFriend(userFriend)){
            return ResponseEntity.ok("删除成功");
        }
        else{
            return ResponseEntity.ok("删除失败");
        }
    }
    @PostMapping("/isMyFriend")
    public ResponseEntity<?> isMyFriend(@RequestBody UserFriend userFriend) {
        if(userFriendService.isFriend(userFriend)){
            return ResponseEntity.ok(Boolean.TRUE);
        }else{
            return ResponseEntity.ok(Boolean.FALSE);
        }
    }
}
