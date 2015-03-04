package com.itu.dataserverlogic;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;




import com.itu.DAO.DataAccess;
import com.itu.action.CommonEnum.OpterationType;
import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction.Builder;
import com.itu.bean.SmartMeterData;
import com.itu.util.ClassDeepCopy;
import com.itu.util.DateUtils;
import com.itu.util.Log4jUtil;

public class FrontSmartMeterSearchLogic
		extends
		CommonProtoLogic<FrontServerSmartMeterDataAction, FrontServerSmartMeterDataAction> {

	// static Logger logger = Log4jUtil.getLogger(FrontSmartMeterLogic.class);

	

	@Override
	public FrontServerSmartMeterDataAction executeAction(
			FrontServerSmartMeterDataAction t) {
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(FrontSmartMeterSearchLogic.class);
		List<Integer> smids = null;
		long  starttime = 0, endtime = 0;
		int time_before_current = 0,intervals = 0;
		FrontServerSmartMeterDataAction.Builder smdataaction = FrontServerSmartMeterDataAction.newBuilder();
		//FrontServerSmartMeterDataAction.Builder smactionbuilder = t.toBuilder();// com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.newBuilder()
		logger.debug("front server search here down is opteration type");
		logger.debug(t.getOperation());
//		logger.debug(t.getIdsList());
		if (t.getOperation().equals(OpterationType.SEARCH))//
		{
			/**
			 * the sartmeter time search parameters
			 */
			time_before_current = t.getTimeBeforeCurrent();
			starttime =  t.getStartTime();
			endtime =  t.getEndTime();
			intervals = t.getInterval();
			smids = t.getSmIdsList();
			logger.debug(time_before_current + starttime + endtime+intervals);
			logger.debug("t.getSmIdsCount()");
			logger.debug(t.getSmIdsCount());
			//logger.debug(smids.get(0));
			
			
			if ((0 != starttime) && (0 != endtime)) {
				
				logger.debug("(0 != starttime) && (0 != endtime)");
				logger.debug("search all the smartmeters with start time");
				smdataaction = getDataFromDatabase(smids,smdataaction,starttime,endtime,intervals);
			/*	if (0 != t.getIdsCount()) {
					*//**
					 * search dedicated the smartmeters
					 *//*
					logger.debug("search dedicated the smartmeters with starttime");
					smdataaction = getDataFromDatabase(smids,smdataaction, starttime,endtime,intervals);

				} else {
					*//**
					 * search all the smartmeters
					 *//*
					logger.debug("search all the smartmeters with start time");
					smdataaction = getDataFromDatabase(smids,smdataaction,starttime,endtime,intervals);

				}*/

			} else {
				logger.debug("(0 == starttime) || (0 == endtime)");
				logger.debug("search dedicated the smartmeters without starttime");
				if (0 == time_before_current)
					time_before_current = 3600;// default 1 hour data;
				smdataaction = getDataFromDatabase(smids,smdataaction,time_before_current,intervals);
				/*if (0 != t.getIdsCount()) {
					*//**
					 * search dedicated the smartmeters
					 *//*
					logger.debug("search dedicated the smartmeters without starttime");
					if (0 == time_before_current)
						time_before_current = 3600;// default 1 hour data;
					smdataaction = getDataFromDatabase(smids,smdataaction,time_before_current,intervals);

				} else {
					*//**
					 * search all the smartmeters
					 *//*
					
					logger.debug("search all the smartmeters without starttime");
					if (0 == time_before_current)
						time_before_current = 3600;// default 5 minutes data;

					smdataaction = getDataFromDatabase(smids,smdataaction,time_before_current,intervals);

				}*/
			}
			//smactionbuilder.addRecordsBuilder(smdataaction.build());
		}
		return smdataaction.build();
	}

	private String setSearchHQL(Integer sm_id ,int seconds,String... parameters) {
		
		Calendar cl = Calendar.getInstance();
		Date endDate = new Date();
		
		cl.setTime(endDate);
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
				+ end + "' and smtable.smIndex = '"+sm_id+"'");
		return hql;
		}
    private String setSearchHQL(Integer sm_id ,long starttime,long endtime,String... parameters) {
		
        	
    	Date starttimeDate =  DateUtils.fromUnixTime(starttime);
		Date endtimeDate =  DateUtils.fromUnixTime(endtime);
		

		
		
		String hql = ("from SmartMeterData smtable where smtable.timestamp > '"
				+ starttimeDate
				+ "' and smtable.timestamp <= '"
				+ endtimeDate + "' and smtable.smIndex = '"+sm_id+"'");// need test
		return hql;
		}
	
	private boolean hibernateListtoProto(List<SmartMeterData> list,
			com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords) {

		for (SmartMeterData hibernatesmdata : list) {

			com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecord.Builder smdata = com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecord.newBuilder();
			if (ClassDeepCopy.CopyBeanToProto(hibernatesmdata, smdata)) {

				onesmdatarecords.addRecords(smdata);

			} else {
				logger.debug("copy error");
				return false;
			}
		}
		return true;
	}
	// fetch data from table and return to Builder
	private Builder getDataFromDatabase(List<Integer> sm_ids ,FrontServerSmartMeterDataAction.Builder smactionbuilder,long starttime,long endtime,int intervals) {
		    logger.debug("////////////////////////");
			logger.debug(intervals);
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
					Calendar cl = Calendar.getInstance();			
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
//						smactionbuilder.setStatus("true");
					else{
						smactionbuilder.setErrMsg("copy error");
						smactionbuilder.setStatus("false");
					}
					logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());
					smactionbuilder.addRecords(onesmdatarecords);
					//
				}
				else {
					logger.debug(intervals+" zero");
					logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
					if (hibernateListtoProto(list, onesmdatarecords))
						onesmdatarecords.setSmId(i);
//						smactionbuilder.setStatus("true");
					else{
						smactionbuilder.setErrMsg("copy error");
						smactionbuilder.setStatus("false");
					}
					logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());
					smactionbuilder.addRecords(onesmdatarecords);
					//
				}	
				//
				
			}
		
			return smactionbuilder;
			/*smactionbuilder.addRecords(value)
			List<SmartMeterData> list = DataAccess.HibernateSearchOperation(setSearchHQL(sm_ids,starttime,endtime));
			Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();
			
			logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
			if (hibernateListtoProto(list, smdatarecords))
				smactionbuilder.setStatus("true");
			else {
				smactionbuilder.setErrMsg("copy error");
				smactionbuilder.setStatus("false");
			}
			logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());*/
			

		}
	private Builder getDataFromDatabase(List<Integer> sm_ids ,FrontServerSmartMeterDataAction.Builder smactionbuilder,int seconds,int intervals) {
		
		  logger.debug("////////////////////////");
			logger.debug(intervals);
		//every sm_id  access once databases
		for(int i =0 ; i< sm_ids.size();i++){
			com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.Builder onesmdatarecords = com.itu.action.FrontServerSmartMeterDataActionProtos.SmartMeterRecords.newBuilder();

			//set the search HQL
			//already sorted by the means of timestamp
			List<SmartMeterData> list = DataAccess.HibernateSearchOperation(setSearchHQL(sm_ids.get(i),seconds));
			if(intervals >0) 
			{
				logger.debug("list size ++++"+list.size());
				logger.debug(list.get(0).getTimestamp());
				List<SmartMeterData> newlist =new ArrayList<SmartMeterData>();
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
				for(int i1 = 0 ;i1<list.size();i1++)
				{
					SmartMeterData oneData  = list.get(i1);
					
					if(oneData.getTimestamp().after(cl.getTime()))
					{
						cl.add(Calendar.SECOND, intervals);
						newlist.add(oneData);
					}
				
				}
				for(SmartMeterData a : newlist){
					logger.debug("++++++++++++++++++++++++++++++++++++> "); 
		                 System.out.println(a.getId());
		         }
				logger.debug("++++++++++++++++++++++++++++++++++++> "+list.size()+"-----------"+newlist.size());
				logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
				if (hibernateListtoProto(newlist, onesmdatarecords))
					onesmdatarecords.setSmId(i);
	//				smactionbuilder.setStatus("true");
				else{
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());
				smactionbuilder.addRecords(onesmdatarecords);
			}
			else{
				logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
				if (hibernateListtoProto(list, onesmdatarecords))
					onesmdatarecords.setSmId(i);
	//				smactionbuilder.setStatus("true");
				else{
					smactionbuilder.setErrMsg("copy error");
					smactionbuilder.setStatus("false");
				}
				logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());
				smactionbuilder.addRecords(onesmdatarecords);
			}
			//
		}
	
		return smactionbuilder;
		/*smactionbuilder.addRecords(value)
		List<SmartMeterData> list = DataAccess.HibernateSearchOperation(setSearchHQL(sm_ids,starttime,endtime));
		Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();
		
		logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
		if (hibernateListtoProto(list, smdatarecords))
			smactionbuilder.setStatus("true");
		else {
			smactionbuilder.setErrMsg("copy error");
			smactionbuilder.setStatus("false");
		}
		logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());*/
		

	}
		
	

		@Override
		public FrontServerSmartMeterDataAction executeAction() {
			// TODO Auto-generated method stub
			return null;
		}
}
