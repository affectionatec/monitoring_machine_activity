package uk.ac.cardiff.mma.application.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration

@ComponentScan
@EnableJpaRepositories(
		basePackages = "uk.ac.cardiff.mma.application.equipment.repositories",
        entityManagerFactoryRef = "applicationEntityManagerFactory",
        transactionManagerRef = "applicationTransactionManager"
)
public class ApplicationDataSourceConfig {

	@Autowired
	private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.application")
    public DataSource ApplicationDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "applicationTransactionManager")
    public PlatformTransactionManager applicationTransactionManager()
    {
        EntityManagerFactory entityManagerFactory = applicationEntityManagerFactory().getObject();
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean applicationEntityManagerFactory()
    {
    	LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(ApplicationDataSource());
        entityManagerFactory.setPackagesToScan(new String[]{"uk.ac.cardiff.mma.application.equipment.entity"});
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setPersistenceUnitName("ApplicationDb");
        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        entityManagerFactory.setJpaProperties(jpaProperties);

        return entityManagerFactory;

    }



    @Bean
    public JdbcTemplate jdbcTemplate(@Qualifier("ApplicationDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
