package com.shinwootns.ipm;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;

import com.shinwootns.ipm.data.entity.AuthType;
import com.shinwootns.ipm.data.entity.SiteInfo;
import com.shinwootns.ipm.data.mapper.AuthTypeMapper;
import com.shinwootns.ipm.data.mapper.SiteInfoMapper;
import com.shinwootns.ipm.property.SystemPropertiesValidator;

//@ComponentScan(basePackages = "com.shinwootns.ipm")
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@EnableScheduling
@SpringBootApplication
public class SWTManagerApplication implements CommandLineRunner {

	//@Autowired
	//private SiteInfoMapper siteMapper;
	
	//@Autowired
	//private AuthTypeMapper autTypeMapper;
	
	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(SWTManagerApplication.class);
		SpringApplication app = appBuilder.build();
		app.run(args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		System.out.println("Application Start...");
		
		/*
		//findAll
		System.out.println("===============================================");
		for(AuthType authType : autTypeMapper.findAll()) {
			System.out.println(String.format("[AUTHTYPE] code:%s, name:%s", authType.getAuthType(), authType.getAuthName()));
		}
		
		//findById
		System.out.println("===============================================");
		SiteInfo site = siteMapper.findById(1);
		System.out.println(String.format("[SITE] id:%d, code:%s, name:%s", site.getSiteId(), site.getSiteCode(), site.getSiteName()));
		System.out.println("===============================================");
		*/
	}
}
