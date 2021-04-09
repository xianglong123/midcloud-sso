package com.cmft.server;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;

/**
 * @author xuxueli 2018-03-22 23:41:47
 */
@MapperScan(basePackages = "com.cmft.dao")
@SpringBootApplication
public class XxlSsoServerApplication {

	public static void main(String[] args) {
        SpringApplication.run(XxlSsoServerApplication.class, args);
	}

}
