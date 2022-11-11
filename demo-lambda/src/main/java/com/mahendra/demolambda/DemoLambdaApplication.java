package com.mahendra.demolambda;

import java.util.function.Function;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DemoLambdaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoLambdaApplication.class, args);
	}

	@Bean
	public Function<String,String> uppercase(){
		return value -> {
			if(value.equals("exception")) {
				throw new RuntimeException("The error ");
			}
			else {
				return value.toUpperCase();
			}
		};
	}
}
