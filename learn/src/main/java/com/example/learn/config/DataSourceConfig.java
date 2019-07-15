package com.example.learn.config;

import java.sql.SQLException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 
 * @author e5552298 Create data source manually for spring boot application
 *
 */
@Configuration
@EnableTransactionManagement
public class DataSourceConfig {

	@Autowired
	private Environment env;

	@Configuration
	@PropertySource({ "classpath:db.properties" })

	static class DEFAULT {
	}

	public enum DbProperty {
		user("spring.datasource.username"), pass("spring.datasource.password"), url("spring.datasource.url"), dialect(
				"spring.jpa.database-platform"), packages(
						"hibernate.packagestoscan"), ddl("spring.jpa.hibernate.ddl-auto");

		private String property = "";

		private DbProperty(String property) {
			this.property = property;
		}

		public String getProperty() {
			return property;
		}
	}

	/**
	 * @return EntityManagerFactoryBean
	 * @throws SQLException
	 *             If an SQL Exception occurred
	 */
	@Primary
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws SQLException {
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource());
		em.setPackagesToScan(new String[] { getValue(DbProperty.packages) });

		JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
		em.setJpaVendorAdapter(vendorAdapter);
		em.setJpaProperties(additionalProperties());

		return em;
	}

	/**
	 * Creating the datasource
	 * 
	 * @return
	 * @throws SQLException
	 */
	@Primary
	@Bean
	DataSource dataSource() throws SQLException {
		OracleDataSource dataSource = new OracleDataSource();
		dataSource.setUser(getValue(DbProperty.user));
		dataSource.setPassword(getValue(DbProperty.pass));
		dataSource.setURL(getValue(DbProperty.url));
		dataSource.setImplicitCachingEnabled(true);
		dataSource.setFastConnectionFailoverEnabled(true);
		return dataSource;
	}

	/**
	 * @param property
	 *            The DbProperty to find
	 * @return The value of the DbProperty
	 */
	private String getValue(DbProperty property) {
		return env.getProperty(property.getProperty());
	}

	@Primary
	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
		return new JpaTransactionManager(emf);
	}

	private Properties additionalProperties() {
		Properties properties = new Properties();
		properties.setProperty("hibernate.dialect", getValue(DbProperty.dialect));
		//properties.setProperty("hibernate.ddl-auto", getValue(DbProperty.ddl));
		return properties;
	}
}
