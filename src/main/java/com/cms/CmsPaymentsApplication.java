package com.cms;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@SpringBootApplication
public class CmsPaymentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CmsPaymentsApplication.class, args);
	}
	@Primary
	@Bean
	public DataSource dataSource() {
	  return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.H2)
	      .addScript("/org/springframework/batch/core/schema-h2.sql")
	      .generateUniqueName(true).build();
	}
}
