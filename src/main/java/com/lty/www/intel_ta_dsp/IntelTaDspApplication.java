package com.lty.www.intel_ta_dsp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.lty.www.intel_ta_dsp.mapper")
public class IntelTaDspApplication {

    public static void main(String[] args) {
        SpringApplication.run(IntelTaDspApplication.class, args);
    }


}
