package com.ak.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;


@Configuration
@EnableWebMvc
@ComponentScan("com.ak")
@EnableTransactionManagement //zarzadzal bedzie spring automatycznie transakcjami
@EnableScheduling
@EnableAsync
//@PropertySource(value = "classpath:messages/messages_en.properties", ignoreResourceNotFound=false) // acces from Environment env -> env.getProperty ("propertyName");
public class AppConfig extends WebMvcConfigurerAdapter
{
	public final static String EMAIL = "test54321010@gmail.com"; //mail spamujący
	private final static String PASSWORD = "testpass123"; // haslo do maila spamujacego

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable(); //wlacza domyslna konfiguracje mvc, ktore zajmuje sie przekierowaniem zadan
	}

	
	//fabryka obiektu - przygotowuje w odpowiedni sposob obiekt, ktory bedzie Autowired-owany
	@Bean //zwraca obiekt, ktory zawiera informacje o widoku - interfejsie graficznym
	public ViewResolver viewResolver()
	{
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setPrefix("/WEB-INF/"); //tu bedziem front dodawac - jsp
		viewResolver.setSuffix(".jsp");
		//TODO check viewResolver adding properties!
//		//--------- ADD Properties to viewResolver() --------------- 
//		//something wrong! check it later
//		Resource resources = new ClassPathResource("messages/messages2.properties");
//		try {
//		System.out.println(PropertiesLoaderUtils.loadProperties(resources)); //visible
//			viewResolver.setAttributes(PropertiesLoaderUtils.loadProperties(resources)); // not visible from Environment env.getProperty("propertyName");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
			
		return viewResolver;
	}
	
	//fabryka obiektu - przygotowuje w odpowiedni sposob obiekt, ktory bedzie Autowired-owany
	@Bean //tu definiujemy sposob na budowanie beana
	public JavaMailSender javaMailSender()
	{
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setPort(587);
		
		//nazwa konta, z jakiego bedziemy wysylac maile
		javaMailSenderImpl.setUsername(EMAIL);
		javaMailSenderImpl.setPassword(PASSWORD);
		
		javaMailSenderImpl.getJavaMailProperties().setProperty("mail.smtp.auth", "true");
		javaMailSenderImpl.getJavaMailProperties().setProperty("mail.smtp.starttls.enable", "true");
		
		return javaMailSenderImpl;
	}
	
	@Bean
	public FreeMarkerConfigurationFactoryBean getFreeMarkerConfiguration() {
		FreeMarkerConfigurationFactoryBean bean = new FreeMarkerConfigurationFactoryBean();
		bean.setTemplateLoaderPath("/WEB-INF/templates/");
		return bean;
	}
	
 
	//??
//	@Bean
//	public AdvService getAdvService(){
//		return new AdvService();
//	}
	
	
}