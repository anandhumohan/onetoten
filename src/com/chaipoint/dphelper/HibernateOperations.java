package com.chaipoint.dphelper;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateOperations {

	public void save(Criteria c) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		Integer i = (Integer) session.save(c);
		System.out.println("Save:" + i);
		session.getTransaction().commit();
		session.close();
	}

	public Object get(Criteria c) {
		SessionFactory factory = new Configuration().configure().buildSessionFactory();
		Session session = factory.openSession();
		session.beginTransaction();
		Object list = c.list();
		System.out.println("Get");
		session.getTransaction().commit();
		session.close();
		return list;

	}

}
