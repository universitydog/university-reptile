package com.dao;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DataRegistery {

	private static ApplicationContext context = new ClassPathXmlApplicationContext("com/etc/spring/spring-date.xml");
	
	/**
	 * 
	 * @return SessionFactory
	 */
	public static SessionFactory getSessionFactory() {
		return context.getBean("sessionFactory", SessionFactory.class);
	}
	
}
