package com.video;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

/**
 * @author wangsihang
 * @date 2021/3/23 下午10:05
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.video.mapper"})
@ComponentScan(basePackages = {"com.video", "org.n3r.idworker"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
