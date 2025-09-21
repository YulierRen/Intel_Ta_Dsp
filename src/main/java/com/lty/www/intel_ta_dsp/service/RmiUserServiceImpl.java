package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.entity.User;
import org.springframework.stereotype.Service;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

@Service
public class RmiUserServiceImpl extends UnicastRemoteObject implements RmiUserService {
    private final UserService userService;

    public RmiUserServiceImpl(UserService userService) throws RemoteException {
        super();
        this.userService = userService;
    }

    @Override
    public List<User> getAllUsers() throws RemoteException {
        return userService.findAll();
    }

    @Override
    public User getUserByUsername(String username) throws RemoteException {
        return userService.findByUsername(username);
    }
}
