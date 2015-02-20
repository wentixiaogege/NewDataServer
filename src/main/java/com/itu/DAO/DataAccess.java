package com.itu.DAO;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.itu.bean.CloudCommand;
import com.itu.bean.Command;

import com.itu.util.ClassDeepCopy;
import com.itu.util.DateUtils;
import com.itu.util.HibernateUtil;
import com.itu.util.Log4jUtil;

public class DataAccess {
	static Logger logger = Log4jUtil.getLogger(DataAccess.class);
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();

	public  static <T> boolean addOperation(T[] add) {
		logger.debug("add  begin..");

		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(add);// 执行
			tran.commit();// 提交
		} catch (Exception e) {
			logger.debug("add failed", e);
			return false;
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("add  end..");
		return true;

	}
	public  static <T> boolean updateOperation(T update) {
		logger.debug("addNewCommand begin..");

		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(update);// 执行
			tran.commit();// 提交
		} catch (Exception e) {
			logger.debug("failed", e);
			return false;
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("addNewCommand end..");
		return true;

	}
	public  static <T> boolean deleteOperation(T delete) {
		logger.debug("addNewCommand begin..");

		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(delete);// 执行
			tran.commit();// 提交
		} catch (Exception e) {
			logger.debug("failed", e);
			return false;
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("addNewCommand end..");
		return true;

	}
	public  static <T> List<T> searchOperation(String get) {
		logger.debug("get data begin..");
		List<T> list = null;
		try {
			s = factory.openSession();
			
			Query query = s.createQuery(get);
			list = query.list();
			
		} catch (Exception e) {
			logger.debug("failed", e);
			
		} finally {
			if (s != null && s.isOpen())
				s.close();
		}
		logger.debug("get data end..");
		return list;

	}
}
