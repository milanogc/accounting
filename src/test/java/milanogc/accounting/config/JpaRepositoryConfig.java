package milanogc.accounting.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories({"milanogc.accounting.account", "milanogc.accounting.entry", "milanogc.accounting.posting"})
@EnableTransactionManagement
public class JpaRepositoryConfig {
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
		LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
		factoryBean.setPackagesToScan("");
		factoryBean.setDataSource(dataSource());
		factoryBean.setJpaVendorAdapter(vendorAdapter());
		factoryBean.setJpaProperties(additionlProperties());
		return factoryBean;
	}

	@Bean
	public EntityManagerFactory entityManagerFactory() {
		return entityManagerFactoryBean().getObject();
	}

	private JpaVendorAdapter vendorAdapter() {
		return new HibernateJpaVendorAdapter() {{
			setDatabasePlatform("org.hibernate.dialect.H2Dialect");
		}};
	}

	private Properties additionlProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		/*properties.put("hibernate.show_sql", "true");*/
		return properties;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl("jdbc:h2:~/accounting");
		dataSource.setUsername("admin");
		dataSource.setPassword("admin");
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactoryBean().getObject());
		return transactionManager;
	}

	/**
	 * Exception translation - translating the low level exceptions - which tie
	 * the API to JPA - into higher level, generic Spring exceptions.
	 */
	@Bean
	public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
		return new PersistenceExceptionTranslationPostProcessor();
	}
}