package com.itu.dataserverlogic;

import java.text.SimpleDateFormat;
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
		List<String> smieeeaddress = null;
		long  starttime = 0, endtime = 0;
		int time_before_current = 0;
		FrontServerSmartMeterDataAction.Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();
		//FrontServerSmartMeterDataAction.Builder smactionbuilder = t.toBuilder();// com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.newBuilder()
		logger.debug("front server search here down is opteration type");
		logger.debug(t.getOperation());
		logger.debug(t.getSmIeeeAddressList());
		if (t.getOperation().equals(OpterationType.SEARCH))//
		{
			// set the valu  es
			time_before_current = t.getTimeBeforeCurrent();
			starttime =  t.getStartTime();
			endtime =  t.getEndTime();

			logger.debug(time_before_current + starttime + endtime);

			
			if ((0 != starttime) && (0 != endtime)) {
				
				logger.debug("(0 != starttime) && (0 != endtime)");
				smieeeaddress = t.getSmIeeeAddressList();
				if (0 == t.getSmIeeeAddressCount()) {
					/**
					 * search dedicated the smartmeters
					 */
					logger.debug("search dedicated the smartmeters with starttime");
				
					smdatarecords = getDataFromDatabase(smdatarecords, setSearchHQL(starttime,endtime));

				} else {
					/**
					 * search all the smartmeters
					 */
					logger.debug("search all the smartmeters with start time");
					
					smdatarecords = getDataFromDatabase(smdatarecords, setSearchHQL(starttime,endtime));

				}

			} else {
				logger.debug("(0 == starttime) || (0 == endtime)");
				if (0 != t.getSmIeeeAddressCount()) {
					/**
					 * search dedicated the smartmeters
					 */
					logger.debug("search dedicated the smartmeters without starttime");
					if (0 == time_before_current)
						time_before_current = 3600;// default 1 hour data;
		
					smdatarecords = getDataFromDatabase(smdatarecords, setSearchHQL(time_before_current));

				} else {
					/**
					 * search all the smartmeters
					 */
					logger.debug("search all the smartmeters without starttime");
					if (0 == time_before_current)
						time_before_current = 3600;// default 5 minutes data;

					smdatarecords = getDataFromDatabase(smdatarecords, setSearchHQL(time_before_current));

				}
			}
			//smactionbuilder.addRecordsBuilder(smdatarecords.build());
		}
		return smdatarecords.build();
	}

	private String setSearchHQL(int seconds,String... parameters) {
		
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
				+ end + "'");
		return hql;
		}
        private String setSearchHQL(long starttime,long endtime,String... parameters) {
		
        	
    	Date starttimeDate =  DateUtils.fromUnixTime(starttime);
		Date endtimeDate =  DateUtils.fromUnixTime(endtime);
		
		
		
		String hql = ("from SmartMeterData smtable where smtable.timestamp > '"
				+ starttimeDate
				+ "' and smtable.timestamp <= '"
				+ endtimeDate + "'");// need test
		return hql;
		}
	
	private boolean hibernateListtoProto(List<SmartMeterData> list,
			Builder smdatarecords) {

		for (SmartMeterData hibernatesmdata : list) {
//			logger.info(String.format(
//					"localserver getting a new data energey is :%s",
//					hibernatesmdata.getEnergy()));
			com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecord.Builder smdata = com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecord.newBuilder();
			if (ClassDeepCopy.CopyBeanToProto(hibernatesmdata, smdata)) {

				smdatarecords.addRecords(smdata);
//				logger.debug("copy hibernate to proto right");

			} else {
				logger.debug("copy error");
				// smactionbuilder.setErrMsg("copy error");
				// smactionbuilder.setStatus("false");
				return false;
			}
		}
		return true;
	}
	// fetch data from table and return to Builder
	private Builder getDataFromDatabase(FrontServerSmartMeterDataAction.Builder smactionbuilder, String hql) {
			List<SmartMeterData> list = DataAccess.HibernateSearchOperation(hql);
			Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();
			logger.debug("hibernateListtoProtoget data begin.."+System.nanoTime());
			if (hibernateListtoProto(list, smdatarecords))
				smactionbuilder.setStatus("true");
			else {
				smactionbuilder.setErrMsg("copy error");
				smactionbuilder.setStatus("false");
			}
			logger.debug("hibernateListtoProtoget data end.."+System.nanoTime());
			return smdatarecords;

		}

		@Override
		public FrontServerSmartMeterDataAction executeAction() {
			// TODO Auto-generated method stub
			return null;
		}
}
