package com.dotcom.costaverde;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

//@Configuration
//@Profile("dev")
public class ServletInitializer extends SpringBootServletInitializer {
	
	//@Value("${server.servlet.context-path}")
  //private String contextPath;
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		System.out.println("ServletInitializer.configure");
		return application.sources(CostaverdePositionApplication.class);
	}
}
