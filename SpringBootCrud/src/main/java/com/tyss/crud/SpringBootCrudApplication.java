package com.tyss.crud;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.tyss.crud.bean.Student;

@SpringBootApplication
public class SpringBootCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCrudApplication.class, args);
	}
	@Autowired
	private DataSource ds;

	@Bean(name = "sesfact")
	public LocalSessionFactoryBean createLocalSesFact() {
		LocalSessionFactoryBean lsfb = null;
		Properties props = null;
		lsfb = new LocalSessionFactoryBean();
		lsfb.setDataSource(ds);
		lsfb.setAnnotatedClasses(Student.class);
		props = new Properties();
		props.setProperty("show_sql", "true");
		props.setProperty("dialect", "org.hibernate.dialect.MYSQL5Dialect");
		props.setProperty("hbm2ddl.auto", "update");
		lsfb.setHibernateProperties(props);
		return lsfb;
	}

	@Bean(name = "ht")
	public HibernateTemplate createHibernateTemplate() {
		return new HibernateTemplate(createLocalSesFact().getObject());

	}

}
