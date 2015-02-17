package com.itu.DAO;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.itu.bean.CloudCommand;
import com.itu.bean.Command;
import com.itu.util.ClassDeepCopy;
import com.itu.util.HibernateUtil;
import com.itu.util.Log4jUtil;

public class CloudCommandDao {
	static Logger logger = Log4jUtil.getLogger(CloudCommandDao.class);
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();

	public static boolean addNewCommand(CloudCommand cmd) {
		logger.debug("addNewCommand begin..");
		// using enum or using data access
		Command tmpcmd = null;
		cmd.setId(0);
		try {
			s = factory.openSession();
			Transaction tran = s.beginTransaction();// 开始事物
			s.save(cmd);// 执行
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

	public static boolean frontend_addNewCommand(com.itu.myserver.CloudCommandProtos.CloudCommand cmd) {
		// TODO Auto-generated method stub
		Session s = null;
		// using enum or using data access
		Command tmpcmd = null;
		CloudCommand hibernatecmd = new CloudCommand();
		try {
			s = HibernateUtil.getSessionFactory().openSession();

			String getcmdparameters = "from Command as cmd1 where cmd1.id=:id";// 使用命名参数，推荐使用，易读。
			Query query = s.createQuery(getcmdparameters);
			query.setInteger("id", cmd.getId());

			tmpcmd = (Command) query.uniqueResult();
			// setting valuse

			hibernatecmd.setCommandId(tmpcmd.getId());
			hibernatecmd.setDataLength(tmpcmd.getDataLength());
			hibernatecmd.setName(tmpcmd.getName());
			hibernatecmd.setChecked(0);
			hibernatecmd.setTimestamp(new Date());
			hibernatecmd.setCoordinatorId(cmd.getCoordinatorId());
			hibernatecmd.setSmartmeterId(cmd.getSmartmeterId());
			hibernatecmd.setParam1(cmd.getParam1());

			Transaction tran = s.beginTransaction();// 开始事物
			s.save(hibernatecmd);// 执行
			tran.commit();// 提交
			// logger.info(String.format("hibernate adding new command to commandcloud table ------command name is :%s",
			// cmd.getName()));
			/*
			 * for (SmartMeterData smdata1 : list) {
			 * logger.info(String.format("Smart Meter data IEEE address is :%s",
			 * smdata1.getSmIeeeAddress())); }
			 */
		} catch (Exception e) {
			// logger.debug("hibernate adding new command to commandcloud table ------exception:"
			// + e);
			return false;
			// fail(e.getMessage());
		} finally {

			if (s != null)
				s.close();
		}
		return true;

	}
}
