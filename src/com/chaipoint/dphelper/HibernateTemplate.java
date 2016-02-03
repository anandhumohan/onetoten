package com.chaipoint.dphelper;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.JDBCConnectionException;

public class HibernateTemplate {
	private static final Logger logger = Logger.getLogger(HibernateTemplate.class);

	static SessionFactory factory = null;
	private Session session = null;
	private Transaction txn = null;

	public Session getSession() {
		try {
			if (session == null || !session.isOpen()) {
				session = factory.openSession();
				txn = session.beginTransaction();
			}
		} catch (JDBCConnectionException e) {

			logger.error("JDBC Connection Exception   " + e.getStackTrace());
			System.out.println("JDBC Connection Exception   " + e.getStackTrace());

			factory.close();
			factory = new Configuration().configure().buildSessionFactory();
			session = factory.openSession();
			txn = session.beginTransaction();

		}
		return session;
	}

	public HibernateTemplate() {
		if (factory == null || !factory.isClosed()) {
			factory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

		}

	}

	private void postAction() {
		txn.commit();
	}

	private void postSession() {
		if (session.isOpen())
			session.close();

	}

	public String save(Object obj) throws Exception {

		getSession();
		try {
			session.save(obj);
			postAction();
		} catch (JDBCConnectionException e) {
			logger.error("JDBC Connection Exception   " + e.getStackTrace());
			System.out.println("JDBC Connection Exception   " + e.getStackTrace());
			throw new Exception(e);
		} catch (ConstraintViolationException e) {

			logger.error("Duplicate Value Exception while saving " + obj + "using Hibernate Template\n" + e);
			System.out.println("Duplicate Value Exception while saving " + obj + "using Hibernate Template\n" + e);
			throw new ConstraintViolationException("Duplicate Value Exception while saving", new SQLException(),
					obj.getClass() + "");
		} catch (Exception e) {

			logger.error("Exception while saving " + obj + "using Hibernate Template\n" + e);
			System.out.println("Exception while saving " + obj + "using Hibernate Template\n" + e);

			throw new Exception(e);
		} finally {
			postSession();
		}

		return "Success";
	}

	public Object get(Criteria cr) {
		Object responseEntity = null;
		getSession();
		try {
			responseEntity = cr.list();
		} catch (JDBCConnectionException e) {
			logger.error("JDBC Connection Exception   " + e.getStackTrace());
			System.out.println("JDBC Connection Exception   " + e.getStackTrace());

		} catch (Exception ex) {
			logger.error("Exception while fetching data using Hibernate Template " + ex);
			System.out.println("Exception while fetching data using Hibernate Template " + ex);
			return "error";
		} finally {
			postSession();
		}

		return responseEntity;
	}

}
