package com.ssafy.TmT;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackages = "com.ssafy.TmT.dao")
@SpringBootApplication
public class TmTApplication {

	public static void main(String[] args) {
		SpringApplication.run(TmTApplication.class, args);
	}

}
