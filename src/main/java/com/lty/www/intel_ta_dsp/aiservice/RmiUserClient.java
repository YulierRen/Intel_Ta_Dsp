package com.lty.www.intel_ta_dsp.aiservice;

import com.lty.www.intel_ta_dsp.entity.User;
import com.lty.www.intel_ta_dsp.service.RmiUserService;

import java.rmi.Naming;
import java.util.List;

public class RmiUserClient {
    public static void main(String[] args) {
        try {
            RmiUserService rmiUserService = (RmiUserService) Naming.lookup("http://47.104.213.149:1099/RmiUserService");
            List<User> users = rmiUserService.getAllUsers();
            System.out.println("所有用户:");
            users.forEach(System.out::println);
            User user = rmiUserService.getUserByUsername("testuser");
            System.out.println("查询到的用户: " + user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
