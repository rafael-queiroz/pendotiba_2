package br.com.logic.pendotiba.logicbus.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.number.NumberStyleFormatter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

@Configuration
@EnableCaching
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addFormatters(FormatterRegistry registry) {
		NumberStyleFormatter bigDecimalFormatter = new NumberStyleFormatter("#,##0.00");
		registry.addFormatterForFieldType(BigDecimal.class, bigDecimalFormatter);

		DateTimeFormatterRegistrar dateTimeFormatter = new DateTimeFormatterRegistrar();
		dateTimeFormatter.setDateFormatter(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
		dateTimeFormatter.setTimeFormatter(DateTimeFormatter.ofPattern("HH:mm"));
		dateTimeFormatter.registerFormatters(registry);
	}

	/*
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizerNotFound(){
		return ( container -> {
			ErrorPage paginaCustom404 = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
			container.addErrorPages(paginaCustom404);
		});
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizerServerError(){
		return ( container -> {
			ErrorPage paginaCustom500 = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
			container.addErrorPages(paginaCustom500);
		});
	}
	*/
	
	/*
	@Bean
	public AmparoDialect amparoDialect() {
		return new AmparoDialect();
	}
	
	
	@Bean
	public MessageSource messageSource() {
		ReloadableResourceBundleMessageSource bundle = new ReloadableResourceBundleMessageSource();
		bundle.setBasename("classpath:/messages");
		bundle.setDefaultEncoding("UTF-8"); // http://www.utf8-chartable.de/
		return bundle;
	}
	*/

	/*
	@Bean
	public CacheManager cacheManager() {
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder().maximumSize(3).expireAfterAccess(20, TimeUnit.SECONDS);
		GuavaCacheManager cacheManager = new GuavaCacheManager();
		cacheManager.setCacheBuilder(cacheBuilder);
		return cacheManager;
	}
	*/
}
