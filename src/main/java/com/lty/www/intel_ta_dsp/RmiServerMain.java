package com.lty.www.intel_ta_dsp;

import com.lty.www.intel_ta_dsp.service.RmiUserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiServerMain {
    public static void main(String[] args) {
        try {
            LocateRegistry.createRegistry(1099);
            ApplicationContext context = new AnnotationConfigApplicationContext(IntelTaDspApplication.class);
            RmiUserServiceImpl rmiUserService = context.getBean(RmiUserServiceImpl.class);
            Naming.rebind("rmi://localhost:1099/RmiUserService", rmiUserService);
            System.out.println("RMI 服务已启动，地址：rmi://localhost:1099/RmiUserService");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
