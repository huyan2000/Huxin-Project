package com.huyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import tk.mybatis.spring.annotation.MapperScan;

/** 
 * @author 胡琰 
 * @version 创建时间：2019年1月20日 上午11:40:50 
 * @Description:
 */

@SpringBootApplication
// 扫描mybatis mapper包路径
@MapperScan(basePackages="com.huyan.mapper")
// 扫描 所有需要的包, 包含一些自用的工具类包 所在的路径
@ComponentScan(basePackages= {"com.huyan","org.n3r.idworker"})
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
