package com.ak.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class ApplicationInitializer implements WebApplicationInitializer{

	@Override
	public void onStartup(ServletContext servletContext) throws ServletException {
		AnnotationConfigWebApplicationContext appContext =  new AnnotationConfigWebApplicationContext();
		appContext.register(AppConfig.class);
		
		DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);
		
		//chce teraz zarejestrowac obiekt dispatcher servlet
		ServletRegistration.Dynamic servletRegistration = servletContext.addServlet("dispatcher", dispatcherServlet);
		servletRegistration.setLoadOnStartup(1);
		servletRegistration.addMapping("/");
		
		CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		
		servletContext.addFilter("characterEncodingFilter", characterEncodingFilter).addMappingForUrlPatterns(null, true, "/*");;
		
		
		//security - dodaje do kontekstu servletu kolejny filtr tym razem zwiazany z security
		// chcemy aby wszystkie beany , wszystkie requesty z kontrolera były zabezpieczone przez spring security
		DelegatingFilterProxy delegatingFilterProxy = new DelegatingFilterProxy();
		servletContext.addFilter("springSecurityFilterChain", delegatingFilterProxy).addMappingForUrlPatterns(null, true, "/*");
		
		
	}

}
