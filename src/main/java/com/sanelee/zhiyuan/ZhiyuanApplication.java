package com.sanelee.zhiyuan;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.sanelee.zhiyuan.Mapper")
public class ZhiyuanApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhiyuanApplication.class, args);
    }

}
