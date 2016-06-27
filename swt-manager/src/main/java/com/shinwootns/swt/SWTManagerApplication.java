package com.shinwootns.swt;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;

import com.shinwootns.swt.data.entity.AuthType;
import com.shinwootns.swt.data.repository.AuthTypeRepository;
import com.shinwootns.swt.main.SystemPropertiesValidator;

//@ComponentScan(basePackages = "com.shinwootns.swt.controller")
@EnableScheduling
@SpringBootApplication
public class SWTManagerApplication implements CommandLineRunner {

	//@Autowired 
	//private AuthTypeRepository repository;
	
	@Bean
	public Validator configurationPropertiesValidator() {
		return new SystemPropertiesValidator();
	}

	/*
	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource);
		return sqlSessionFactoryBean.getObject();
	}*/
	
	@Override
	public void run(String... arg0) throws Exception {
		System.out.println("Application Start...");
		
		//for (AuthType data : repository.findAll()) {
		//	System.out.println(data.toString());
		//}
	}

	public static void main(String[] args) {
		SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(SWTManagerApplication.class);
		SpringApplication app = appBuilder.build();
		app.run(args);
	}
}
