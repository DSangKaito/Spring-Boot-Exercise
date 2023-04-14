package com.runsystem.springbootdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
//@EnableCaching //------Use Cache
@EnableElasticsearchRepositories(basePackages = "com.runsystem.springbootdemo.elasticsearch.repositories")
@EnableJpaRepositories(basePackages = "com.runsystem.springbootdemo.repositories")
public class SpringbootdemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootdemoApplication.class, args);
	}

}
