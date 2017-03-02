package com.ak.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource(value={"classpath:hibernate.properties"})//powiązuje plik konfiguracji który musi być w rosources
@EnableJpaRepositories(basePackages="com.ak.dao") //tam gdzie mamy dostepy do danych czyli DAO
public class HibernateConfig{
	
	@Autowired
	private Environment environment;
	
	
	//framework sam autowireduje obiekt na podstawie tej definicji - gdzieś, nas nie interesuje gdzie
	@Bean
	public DataSource dataSource(){
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		driverManagerDataSource.setDriverClassName(environment.getRequiredProperty("jdbc.driver.class.name")); //z pliku hibernate.properties
		driverManagerDataSource.setPassword(environment.getRequiredProperty("jdbc.password"));
		driverManagerDataSource.setUsername(environment.getRequiredProperty("jdbc.user.name"));
		driverManagerDataSource.setUrl(environment.getProperty("jdbc.url"));
		
		
		return driverManagerDataSource;
	}
	
	//obiekt fabryki beanów encyjnych -dzieki temu bedzie możliwe ORM (object relation mapping)
	@Bean
	public EntityManagerFactory entityManagerFactory(){
		HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
		
		//pomocniczy obiekt zwiazany z ustawieniami hibernate'a
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", environment.getProperty("hibernate.hbm2ddl.auto")); //nazwa z hibernate.properties -> musi być taka nazwa
		properties.put("hibernate.show_sql", environment.getProperty("hibernate.show_sql")); 
		properties.put("hibernate.format_sql", environment.getProperty("hibernate.format_sql")); 
		properties.put("hibernate.generate_statistics", environment.getProperty("hibernate.generate_statistics")); 
		
		
		//głowny obiekt fabryki beanów
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("com.ak.entity");
		factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
		factoryBean.setJpaProperties(properties);
		factoryBean.setDataSource(dataSource());
		
		
		//produkcja beanów encyjnych była możliwa dopiero gdy zostana zakonczone wszystkie ustawienia
		factoryBean.afterPropertiesSet();
		
		
		return factoryBean.getObject(); //hibernate design pattern
	}
	
	//definicja tworzenia obiektu
	@Bean
	public PlatformTransactionManager transactionManager(){
		JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
		jpaTransactionManager.setEntityManagerFactory(entityManagerFactory());
		return jpaTransactionManager;
	}
	
	

}
