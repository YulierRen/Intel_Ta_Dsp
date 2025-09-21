package com.lty.www.intel_ta_dsp.controller;

import com.alibaba.excel.EasyExcel;
import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/api/user/excel")
@RequiredArgsConstructor
public class UserExcelController {
    private final UserService userService;

    @PostMapping("/import")
    public String importUsers(@RequestParam("file") MultipartFile file) throws IOException {
        EasyExcel excel = new EasyExcel();
        List<User> users = EasyExcel.read(file.getInputStream(), User.class, null).sheet().doReadSync();
        users.forEach(userService::addUser);
        return "导入成功，数量：" + users.size();
    }

    @GetMapping("/export")
    public void exportUsers(HttpServletResponse response) throws IOException {
        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("用户数据", "UTF-8");
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<User> users = userService.findAll();
        EasyExcel.write(response.getOutputStream(), User.class).sheet("用户").doWrite(users);
    }
}
