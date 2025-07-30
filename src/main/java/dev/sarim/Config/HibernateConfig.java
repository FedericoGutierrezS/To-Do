package dev.sarim.Config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
public class HibernateConfig {
			
	@Bean
    public DataSource dataSource() {
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setUrl("jdbc:postgresql://127.0.0.1:1025/testdb");
        ds.setUsername("postgres");
        ds.setPassword("123");
        return ds;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource);
        emf.setPackagesToScan("com.example"); // adjust to your entity package
        emf.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        emf.setMappingResources("orm.xml");

        Properties jpaProps = new Properties();
        jpaProps.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        jpaProps.setProperty("hibernate.hbm2ddl.auto", "update");
        jpaProps.setProperty("hibernate.show_sql", "true");
				        emf.setJpaProperties(jpaProps);

				        return emf;
				    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }
}
