package com.shinwootns.ipm;

import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.Validator;

import com.shinwootns.common.network.SyslogManager;
import com.shinwootns.ipm.data.entity.AuthType;
import com.shinwootns.ipm.data.entity.SiteInfo;
import com.shinwootns.ipm.data.mapper.AuthTypeMapper;
import com.shinwootns.ipm.data.mapper.SiteInfoMapper;
import com.shinwootns.ipm.property.SystemPropertiesValidator;
import com.shinwootns.ipm.service.WorkerPoolManager;
import com.shinwootns.ipm.service.syslog.SyslogReceiveHandlerImpl;

//@ComponentScan(basePackages = "com.shinwootns.ipm")
//@EnableAutoConfiguration(exclude = {HibernateJpaAutoConfiguration.class})
@EnableScheduling
@SpringBootApplication
public class MainApplication implements CommandLineRunner {

	private final Logger _logger = Logger.getLogger(this.getClass());
	
	/*
	@Autowired
	private SiteInfoMapper siteMapper;
	
	@Autowired
	private AuthTypeMapper autTypeMapper;
	*/
	
	public static void main(String[] args) {
		
		//BasicConfigurator.configure();
		
		SpringApplicationBuilder appBuilder = new SpringApplicationBuilder(MainApplication.class);
		SpringApplication app = appBuilder.build();
		
		ConfigurableApplicationContext context = app.run(args);
		
		AppContextProvider.getInstance().setApplicationContext( context );
	}
	
	@Override
	public void run(String... arg0) throws Exception {
		
		System.out.println("Application Start...");
		
		//startService();
	
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
	
	public void startService() {
		
		_logger.info("startService()... start");

		WorkerPoolManager.getInstance().start();
		
		// Start receive handler
		SyslogManager.getInstance().start(new SyslogReceiveHandlerImpl());
		
		_logger.info("endService()... start");
	}
	
public void stopService() {
		
		_logger.info("stopService()... start");
		
		// Stop receive handler
		SyslogManager.getInstance().stop();

		// Stop Analyzer
		WorkerPoolManager.getInstance().stop();
		
		_logger.info("stopService()... end");
	}
}
