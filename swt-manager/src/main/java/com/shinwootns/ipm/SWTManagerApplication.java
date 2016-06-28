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
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;

import com.shinwootns.ipm.data.entity.AuthType;
import com.shinwootns.ipm.data.mapper.AuthTypeMapper;
import com.shinwootns.ipm.property.SystemPropertiesValidator;

//@ComponentScan(basePackages = "com.shinwootns.ipm")
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@EnableScheduling
@SpringBootApplication
public class SWTManagerApplication implements CommandLineRunner {

	@Autowired
	private AuthTypeMapper mapper;
	

//	@Bean
//	public Validator configurationPropertiesValidator() {
//		return new SystemPropertiesValidator();
//	}

//	@Bean
//	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
//		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
//		sqlSessionFactoryBean.setDataSource(dataSource);
//		return sqlSessionFactoryBean.getObject();
//	}

	public static void main(String[] args) {
		
		BasicConfigurator.configure();
		
		SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(SWTManagerApplication.class);
		SpringApplication app = appBuilder.build();
		app.run(args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		System.out.println("Application Start...");

		//System.out.println(mapper.findById(1));
		
		for (AuthType data : mapper.findAll()) {
			 System.out.println(data.toString()); 
		}
	}
}
