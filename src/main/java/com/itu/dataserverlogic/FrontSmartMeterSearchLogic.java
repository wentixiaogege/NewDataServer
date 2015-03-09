package com.itu.dataserverlogic;
/**
 *   Authour jack
 */

/**
 *   Authour jack
 */

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
<<<<<<< HEAD
import com.itu.DAO.DataAccess;
import com.itu.action.CommonEnum.OpterationType;
import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
=======

import javassist.expr.NewArray;

import com.itu.DAO.DataAccess;
>>>>>>> jack
//import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction.Builder;
import com.itu.bean.SmartMeterData;

<<<<<<< HEAD
=======
import edu.itu.proto.CommonEnum.OpterationType;
import edu.itu.proto.FrontServerSmartMeterDataActionProtos;
import edu.itu.proto.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import edu.itu.proto.FrontServerSmartMeterDataRecordProtos;
import edu.itu.util.ItuStaticUtil;
import edu.itu.util.ClassDeepCopy;
import edu.itu.util.DateUtils;
import edu.itu.util.Log4jUtil;


>>>>>>> jack
public class FrontSmartMeterSearchLogic extends CommonProtoLogic<FrontServerSmartMeterDataAction, FrontServerSmartMeterDataAction> {

	@Override
	public FrontServerSmartMeterDataAction executeAction(FrontServerSmartMeterDataAction t) {
		
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(FrontSmartMeterSearchLogic.class);
		List<Integer> smids = null;
		long starttime = 0, endtime = 0;
		int time_before_current = 0, intervals = 0;
		FrontServerSmartMeterDataAction.Builder smdataaction = FrontServerSmartMeterDataAction.newBuilder();
		// FrontServerSmartMeterDataAction.Builder smactionbuilder =
		// t.toBuilder();//
		// com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.newBuilder()
		logger.debug("front server search here down is opteration type");
		logger.debug(t.getOperation());
		// logger.debug(t.getIdsList());
		if (t.getOperation().equals(OpterationType.SEARCH))//
		{
			/**
			 * the smartmeter time search parameters
			 */
			time_before_current = t.getTimeBeforeCurrent();
			starttime = t.getStartTime();
			endtime = t.getEndTime();
			intervals = t.getInterval();
			smids = t.getSmIdsList();
			logger.debug(time_before_current + starttime + endtime + intervals);
			logger.debug("t.getSmIdsCount()");
			logger.debug(t.getSmIdsCount());
<<<<<<< HEAD
			
=======

>>>>>>> jack
			if ((0 != starttime) && (0 != endtime)) {

				logger.debug("(0 != starttime) && (0 != endtime)");
				logger.debug("search all the smartmeters with start time");
<<<<<<< HEAD
				getDataFromDatabase(smids,smdataaction,starttime,endtime,intervals);
=======
				getDataFromDatabase(smids, smdataaction, starttime, endtime, intervals);
>>>>>>> jack

			} else {
				logger.debug("(0 == starttime) || (0 == endtime)");
				logger.debug("search dedicated the smartmeters without starttime");
				if (0 == time_before_current)
					time_before_current = 3600;// default 1 hour data;
<<<<<<< HEAD
				getDataFromDatabase(smids,smdataaction,time_before_current,intervals);
=======
				getDataFromDatabase(smids, smdataaction, time_before_current, intervals);
>>>>>>> jack
			}
		}
		return smdataaction.build();
	}
<<<<<<< HEAD
/**
 * 
 * @param sm_id
 * @param seconds
 * @param parameters
 * @return
 */
	private String setSearchHQL(Integer sm_id ,int seconds,String... parameters) {
		
=======

	/**
	 * 
	 * @param sm_id
	 * @param seconds
	 * @param parameters
	 * @return
	 */
	private String setSearchHQL(Integer sm_id, int seconds, String... parameters) {

>>>>>>> jack
		Calendar cl = Calendar.getInstance();
		SimpleDateFormat dd = new SimpleDateFormat(ItuStaticUtil.FOMAT_DATE);

		Date endDate = new Date();

		cl.setTime(endDate);
		cl.add(Calendar.SECOND, -seconds);
		
<<<<<<< HEAD
		SimpleDateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String start = dd.format(startDate);
=======

		String start = dd.format(cl.getTime());
>>>>>>> jack
		String end = dd.format(endDate);

		logger.debug("00000" + start);
		logger.debug("11111" + end);
		String hql = ("from SmartMeterData smtable where smtable.timestamp > '" + start + "' and smtable.timestamp <= '" + end + "' and smtable.smIndex = '" + sm_id + "'");
		return hql;
	}

	/**
	 * 
	 * @param sm_id
	 * @param starttime
	 * @param endtime
	 * @param parameters
	 * @return
	 */
	private String setSearchHQL(Integer sm_id, long starttime, long endtime, String... parameters) {

		String start ,end;
		SimpleDateFormat dd = new SimpleDateFormat(ItuStaticUtil.FOMAT_DATE);

	
		if(0 ==endtime){
			start = dd.format(DateUtils.fromUnixTime(starttime));
			end = dd.format(new Date());// for now
		}else {
			start = dd.format(DateUtils.fromUnixTime(starttime));
			end = dd.format(DateUtils.fromUnixTime(endtime));
		}
<<<<<<< HEAD
/**
	 * 
	 * @param sm_id
	 * @param starttime
	 * @param endtime
	 * @param parameters
	 * @return
	 */
    private String setSearchHQL(Integer sm_id ,long starttime,long endtime,String... parameters) {
		
        	
    	Date starttimeDate =  DateUtils.fromUnixTime(starttime);
		Date endtimeDate =  DateUtils.fromUnixTime(endtime);
		
=======
	   
>>>>>>> jack

		logger.debug("long starttime, long endtime" + start);
		logger.debug("long starttime, long endtime" + end);
		String hql = ("from SmartMeterData smtable where smtable.timestamp > '" + start + "' and smtable.timestamp <= '" + end + "' and smtable.smIndex = '" + sm_id + "'");// need
																																															// test
		return hql;
<<<<<<< HEAD
		}
=======
	}

>>>>>>> jack
	/**
	 * 
	 * @param list
	 * @param onesmdatarecords
	 * @return
	 */
<<<<<<< HEAD
	private boolean hibernateListtoProto(List<SmartMeterData> list,
			com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords) {
=======
	private boolean hibernateListtoProto(List<SmartMeterData> list, FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords) {
>>>>>>> jack

		for (SmartMeterData hibernatesmdata : list) {

			FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecord.Builder smdata = FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecord.newBuilder();
			if (ClassDeepCopy.CopyBeanToProto(hibernatesmdata, smdata)) {

				onesmdatarecords.addRecords(smdata);

			} else {
				logger.debug("copy error");
				return false;
			}
		}
		return true;
	}
<<<<<<< HEAD
=======

>>>>>>> jack
	/**
	 * 
	 * @param sm_ids
	 * @param smactionbuilder
	 * @param starttime
	 * @param endtime
	 * @param intervals
	 * @return
	 */
	// fetch data from table and return to Builder
<<<<<<< HEAD
	private void getDataFromDatabase(List<Integer> sm_ids ,FrontServerSmartMeterDataAction.Builder smactionbuilder,long starttime,long endtime,int intervals) {
		    logger.debug("////////////////////////");
			logger.debug(intervals);
			Calendar cl = Calendar.getInstance();	
			//every sm_id  access once databases
			for(int i =0 ; i< sm_ids.size();i++){
				com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords = com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.newBuilder();

				//set the search HQL
				List<SmartMeterData> list = DataAccess.HibernateSearchOperation(setSearchHQL(sm_ids.get(i),starttime,endtime));
				
				if(intervals >0){
					List<SmartMeterData> newlist =new ArrayList<SmartMeterData>();
					/**
					 * 
					 * 
					 * get the interval data from here
					*/
					cl.setTime(list.get(0).getTimestamp());
					newlist.add(list.get(0));
					cl.add(Calendar.SECOND, intervals);
					System.out.println(cl.getTime());
					for(int i1 = 0 ;i1<list.size();i1++)
					{
						SmartMeterData oneData  = list.get(i1);
						
						if(oneData.getTimestamp().after(cl.getTime()))
						{
							cl.add(Calendar.SECOND, intervals);
							newlist.add(oneData);
						}
					
					}
					
					logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
					if (hibernateListtoProto(newlist, onesmdatarecords))
						onesmdatarecords.setSmId(i);
					else{
						smactionbuilder.setErrMsg("copy error");
						smactionbuilder.setStatus("false");
					}
					logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());
					smactionbuilder.addRecords(onesmdatarecords);
=======
	private void getDataFromDatabase(List<Integer> sm_ids, FrontServerSmartMeterDataAction.Builder smactionbuilder, long starttime, long endtime, int intervals) {
		logger.debug("////////////////////////");
		logger.debug(intervals);
		Calendar cl = Calendar.getInstance();
		// every sm_id access once databases
		for (int i = 0; i < sm_ids.size(); i++) {
			FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords = FrontServerSmartMeterDataActionProtos.SmartMeterRecords.newBuilder();

			// set the search HQL
			List<SmartMeterData> list = DataAccess.HibernateSearchOperation(setSearchHQL(sm_ids.get(i), starttime, endtime));

			if (intervals > 0) {
				List<SmartMeterData> newlist = new ArrayList<SmartMeterData>();
				/**
				 * 
				 * 
				 * get the interval data from here
				 */
				if (0 == list.size()) {
					smactionbuilder.setErrMsg("list is zero ");
					return;
				}
				cl.setTime(list.get(0).getTimestamp());
				newlist.add(list.get(0));
				cl.add(Calendar.SECOND, intervals);
				System.out.println(cl.getTime());
				for (int i1 = 0; i1 < list.size(); i1++) {
					SmartMeterData oneData = list.get(i1);

					if (oneData.getTimestamp().after(cl.getTime())) {
						cl.add(Calendar.SECOND, intervals);
						newlist.add(oneData);
					}

>>>>>>> jack
				}

				logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
				if (hibernateListtoProto(newlist, onesmdatarecords))
					onesmdatarecords.setSmId(i);
				else {
<<<<<<< HEAD
					logger.debug(intervals+" zero");
					logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
					if (hibernateListtoProto(list, onesmdatarecords))
						onesmdatarecords.setSmId(i);
					else{
						smactionbuilder.setErrMsg("copy error");
						smactionbuilder.setStatus("false");
					}
					logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());
					smactionbuilder.addRecords(onesmdatarecords);
				}	
			}
//			return smactionbuilder;
		}
=======
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
				smactionbuilder.addRecords(onesmdatarecords);
			} else {
				logger.debug(intervals + " zero");
				logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
				if (hibernateListtoProto(list, onesmdatarecords))
					onesmdatarecords.setSmId(i);
				else {
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
				smactionbuilder.addRecords(onesmdatarecords);
			}
		}
		// return smactionbuilder;
	}

>>>>>>> jack
	/**
	 * 
	 * @param sm_ids
	 * @param smactionbuilder
	 * @param seconds
	 * @param intervals
	 * @return
	 */
<<<<<<< HEAD
	private void getDataFromDatabase(List<Integer> sm_ids ,FrontServerSmartMeterDataAction.Builder smactionbuilder,int seconds,int intervals) {
		
		logger.debug("////////////////////////");
		logger.debug(intervals);
		//every sm_id  access once databases
		for(int i =0 ; i< sm_ids.size();i++){
			com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords = com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.newBuilder();

			//set the search HQL
			List<SmartMeterData> list = DataAccess.HibernateSearchOperation(setSearchHQL(sm_ids.get(i),seconds));
			if(intervals >0) 
			{
				logger.debug("list size ++++"+list.size());
=======
	private void getDataFromDatabase(List<Integer> sm_ids, FrontServerSmartMeterDataAction.Builder smactionbuilder, int seconds, int intervals) {

		logger.debug("////////////////////////");
		logger.debug(intervals);
		// every sm_id access once databases
		for (int i = 0; i < sm_ids.size(); i++) {
			FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords = FrontServerSmartMeterDataActionProtos.SmartMeterRecords.newBuilder();

			// set the search HQL
			List<SmartMeterData> list = DataAccess.HibernateSearchOperation(setSearchHQL(sm_ids.get(i), seconds));
			if (intervals > 0) {
				logger.debug("list size ++++" + list.size());
				if (0 == list.size()) {
					smactionbuilder.setErrMsg("list is zero ");
					return;
				}
>>>>>>> jack
				logger.debug(list.get(0).getTimestamp());
				List<SmartMeterData> newlist = new ArrayList<SmartMeterData>();
				/**
				 * 
				 * 
				 * get the interval data from here
				 */
				
				Calendar cl = Calendar.getInstance();
				cl.setTime(list.get(0).getTimestamp());
				newlist.add(list.get(0));
				cl.add(Calendar.SECOND, intervals);
				System.out.println(cl.getTime());
				for (int i1 = 0; i1 < list.size(); i1++) {
					SmartMeterData oneData = list.get(i1);

					if (oneData.getTimestamp().after(cl.getTime())) {
						cl.add(Calendar.SECOND, intervals);
						newlist.add(oneData);
					}

				}
				for (SmartMeterData a : newlist) {
					logger.debug("++++++++++++++++++++++++++++++++++++> ");
					System.out.println(a.getId());
				}
				logger.debug("++++++++++++++++++++++++++++++++++++> " + list.size() + "-----------" + newlist.size());
				logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
				if (hibernateListtoProto(newlist, onesmdatarecords))
					onesmdatarecords.setSmId(i);
<<<<<<< HEAD
				else{
=======
				else {
>>>>>>> jack
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
				smactionbuilder.addRecords(onesmdatarecords);
			} else {
				logger.debug("hibernateListtoProtoget data begin.." + System.nanoTime());
				if (hibernateListtoProto(list, onesmdatarecords))
					onesmdatarecords.setSmId(i);
<<<<<<< HEAD
				else{
=======
				else {
>>>>>>> jack
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.." + System.nanoTime());
				smactionbuilder.addRecords(onesmdatarecords);
			}
		}
<<<<<<< HEAD
//		return smactionbuilder;
	}
		@Override
		public FrontServerSmartMeterDataAction executeAction() {
			// TODO Auto-generated method stub
			return null;
		}
=======
		// return smactionbuilder;
	}

	@Override
	public FrontServerSmartMeterDataAction executeAction() {
		// TODO Auto-generated method stub
		return null;
	}
>>>>>>> jack
}
