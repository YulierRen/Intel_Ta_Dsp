package com.lty.www.intel_ta_dsp.service;

import com.lty.www.intel_ta_dsp.entity.User;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RmiUserService extends Remote {
    List<User> getAllUsers() throws RemoteException;
    User getUserByUsername(String username) throws RemoteException;
}
