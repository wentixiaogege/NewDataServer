package com.itu.localserverclient;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.itu.bean.SmartMeterData;
import com.itu.util.HibernateUtil;

@SuppressWarnings("deprecation")
public class HibernateGetData {

	/**
	16      * @param args
	17      */
	private static Session s = null;
	private static SessionFactory factory = HibernateUtil.getSessionFactory();
	     /**
	     * @param args
	     */
	    public static void main(String[] args) {
	         // TODO Auto-generated method stub
	         Session session = factory.openSession();
	
	         
	         Transaction tran = session.beginTransaction();
	         
				Date endDate = new Date();

				//创建基于当前时间的日历对象

				Calendar cl = Calendar.getInstance();

				cl.setTime(endDate);
				int seconds = 360000;
				cl.add(Calendar.SECOND, -seconds);
				Date startDate = cl.getTime();
				
				SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				//格式化开始日期和结束日期

				String start = dd.format(startDate);
				String end = dd.format(endDate);
				
				System.out.print("00000"+start);
				System.out.print("11111"+end);
				String hql = ("from SmartMeterData smtable where smtable.timestamp > '"
						+ start
						+ "' and smtable.timestamp <= '"
						+ end + "'");
	         
	         
	         
	         
	         //   采用HQL的方式，
//	         Date begin = java.sql.Date.valueOf("2015-2-11");
//	         Date end = java.sql.Date.valueOf("2015-2-12");
	         //Date end = new Date();
//	         System.out.print("0000000"+end);
	         //for the sql start and end time
//	         String hqlString = "from SmartMeterData a where a.timestamp > '"+begin+"' and a.timestamp <= '"+end+"'";
	         List<SmartMeterData> result = session.createQuery(hql).list();

	         for(SmartMeterData a : result){
	        	     System.out.print("------------>");
	                 System.out.println(a.getId());
	         }
	         
	         
//	         String hqlString = "FROM SmartMeterData where TimeStamp >= DATE(DATE_SUB(NOW(), INTERVAL 20 DAY))";

//	         List<SmartMeterData> result = session.createSQLQuery(hqlString).list();	       
//	         String hqlString = "SELECT * FROM smart_meter_data where timestamp >= DATE(DATE_SUB(NOW(), INTERVAL 20 DAY))";

//	         List<SmartMeterData> result = session.createSQLQuery(hqlString).list();	         
	         //result.forEach(x->{System.out.println(x.toString());});
	         
//	         String sqlWhere = "{alias}.timestamp > DATE_SUB(curdate(), INTERVAL 20 DAY)";
//	         Criteria criteria = session.createCriteria(SmartMeterData.class);
//	         criteria.add(Restrictions.sqlRestriction(sqlWhere));
//	         List<SmartMeterData> result = criteria.list();
	         
	         /*@SuppressWarnings("unchecked")
			 List<SmartMeterData> result = (List<SmartMeterData>)session.createSQLQuery(hqlString).list();

	         result.forEach(x->{System.out.println(x.getId());});*/

	         for(SmartMeterData a : result){
	        	     System.out.print("--------》");
	                 System.out.println(a.getId());
	         }
	         
	         
	 //        采用QBC的方式。
	        /* Date begin = java.sql.Date.valueOf("2015-2-11");
	         Date end = java.sql.Date.valueOf("2015-2-12");
	         Criteria criteria = session.createCriteria(SmartMeterData.class);
	         Criterion creterion = Expression.between("timestamp", begin, end);
	         @SuppressWarnings("unchecked")
			 List<SmartMeterData> result = criteria.add(creterion).list();
	         for(SmartMeterData a : result){
	             System.out.println(a.getId());
	            // System.out.println(a.getTitle());
	            // System.out.println(a.getDate());
	         }*/
	     }

}