package com.example.learn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;

@ComponentScan(basePackages = { "com.example.learn" })
@ServletComponentScan
@EnableAspectJAutoProxy(proxyTargetClass=true)
@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class, SecurityAutoConfiguration.class })
@SpringBootApplication
public class LearnApplication {
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// @Bean
	// public FilterRegistrationBean<AuthrizationFilter> loggingFilter(){
	// FilterRegistrationBean<AuthrizationFilter> registrationBean
	// = new FilterRegistrationBean<>();
	//
	// registrationBean.setFilter(new AuthrizationFilter());
	// registrationBean.addUrlPatterns("/users/*");
	//
	// return registrationBean;
	// }
	//

	@Bean
	public RestTemplate restTemplate() {
		final RestTemplate restTemplate = new RestTemplate();

		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_HTML));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);

		return restTemplate;
	}

	
	public static void main(String[] args) {
		SpringApplication.run(LearnApplication.class, args);

	}

}
