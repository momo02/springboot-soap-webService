package com.hiair.hom.ibes;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.ktds.kbilling.kfrwk.utils.ComnUtils;

@EnableAspectJAutoProxy
@SpringBootApplication/*(scanBasePackages= {"com.hiair.hom.ibes","com.ktds.kbilling"})*/
@ComponentScan(basePackages = { "com.hiair.hom.ibes", "com.ktds.kbilling" }, excludeFilters = {
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.ktds\\.kbilling\\.kfrwk\\.web\\..*"),
		@ComponentScan.Filter(type = FilterType.REGEX, pattern = "com\\.ktds\\.kbilling\\..*Controller"), })
// @MapperScan(basePackages = "com.ktds.kbilling.**.mapper")
@EnableJpaRepositories(basePackages = "com.ktds.kbilling")
@EntityScan(basePackages = "com.ktds.kbilling.*")
public class IbesApplication {

	private static final String LOG_NAME = "log.name";

	public static void main(String[] args) {
		if (StringUtils.isEmpty(System.getProperty(LOG_NAME))) {
			System.setProperty(LOG_NAME, ComnUtils.getHostNm());
		}

		SpringApplication.run(IbesApplication.class, args);
	}

}
