package com.itu.dataserverlogic;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction.Builder;
import com.itu.bean.SmartMeterData;
import com.itu.util.ClassDeepCopy;
import com.itu.util.DateUtils;
import com.itu.util.Log4jUtil;
import com.itu.util.OperationType;



public class FrontSmartMeterLogic extends CommonProtoLogic<FrontServerSmartMeterDataAction,FrontServerSmartMeterDataAction>{

	static Logger logger = Log4jUtil.getLogger(FrontSmartMeterLogic.class);
	
	@Override
	public FrontServerSmartMeterDataAction executeAction(FrontServerSmartMeterDataAction t) {
		// TODO Auto-generated method stub
		List<String> smieeeaddress =null;
		int starttime=0,endtime=0,time_before_current = 0;
	    com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction.Builder smactionbuilder = t.toBuilder();//com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.newBuilder()

		if(t.getOperation().equals(OperationType.SEARCH))//
		{
			//set the values
		    time_before_current= t.getTimeBeforeCurrent();    
			starttime= t.getStartTime();
			
			if((0 !=starttime )&& (0 != endtime))
			{
				if(null != (smieeeaddress = t.getSmIeeeAddressList())){
					/**
					 * search dedicated the smartmeters
					 */
					//("from A a where a.date > :beginTime and a.date <= :endTime").setTimestamp("beginTime", begin).setTimestamp("endTime", end).list();
					String hql = ("from SmartMeterData smtable where smtable.timestamp > '"+starttime+"' and smtable.timestamp <= '"+endtime+"'");// need test 
					List<SmartMeterData> list = DataAccess.searchOperation(hql);
					Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();
					if(hibernateListtoProto(list,smdatarecords))
						 smactionbuilder.setStatus("true");
					else
					{
						 smactionbuilder.setErrMsg("copy error");
						 smactionbuilder.setStatus("false");
					}
				}else{
					/**
					 * search all the smartmeters
					 */					
					   String hql = ("from SmartMeterData smtable where smtable.timestamp > '"+starttime+"' and smtable.timestamp <= '"+endtime+"'");// need test 
					   List<SmartMeterData> list = DataAccess.searchOperation(hql);
					   Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();

					//com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecords.Builder  smdatarecords = com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecords.newBuilder();
					if(hibernateListtoProto(list,smdatarecords))
						 smactionbuilder.setStatus("true");
					else
					{
						 smactionbuilder.setErrMsg("copy error");
						 smactionbuilder.setStatus("false");
					}
			  }
				
			}else
			{
				if(null != (smieeeaddress = t.getSmIeeeAddressList())){
					/**
					 * search dedicated the smartmeters
					 */
					if(0 == time_before_current) 
						time_before_current = 5;//default 5 minutes data;
					//("from A a where a.date > :beginTime and a.date <= :endTime").setTimestamp("beginTime", begin).setTimestamp("endTime", end).list();
					String hql = ("from SmartMeterData smtable where smtable.timestamp > '"+starttime+"' and smtable.timestamp <= '"+endtime+"'");// need test 
					List<SmartMeterData> list = DataAccess.searchOperation(hql);
					Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();
					if(hibernateListtoProto(list,smdatarecords))
						 smactionbuilder.setStatus("true");
					else
					{
						 smactionbuilder.setErrMsg("copy error");
						 smactionbuilder.setStatus("false");
					}
				}else{
					/**
					 * search all the smartmeters
					 */
					if(0 == time_before_current) 
						time_before_current = 5;//default 5 minutes data;
					
					   String hql = ("from SmartMeterData smtable where smtable.timestamp > '"+starttime+"' and smtable.timestamp <= '"+endtime+"'");// need test 
					   List<SmartMeterData> list = DataAccess.searchOperation(hql);
					   Builder smdatarecords = FrontServerSmartMeterDataAction.newBuilder();

					//com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecords.Builder  smdatarecords = com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecords.newBuilder();
					if(hibernateListtoProto(list,smdatarecords))
						 smactionbuilder.setStatus("true");
					else
					{
						 smactionbuilder.setErrMsg("copy error");
						 smactionbuilder.setStatus("false");
					}
			    } 	
			}
			
		}
	    return smactionbuilder.build();
	}

	@Override
	public FrontServerSmartMeterDataAction executeAction() {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean hibernateListtoProto(List<SmartMeterData> list,Builder smdatarecords)
	{
		
		for (SmartMeterData hibernatesmdata : list) {
			 logger.info(String.format("localserver getting a new data energey is :%s",
					 hibernatesmdata.getEnergy())); 
			 com.itu.action.FrontServerSmartMeterDataRecordProtos.FrontServerSmartMeterDataRecord.Builder smdata = null;
				if (ClassDeepCopy.CopyBeanToProto(hibernatesmdata, smdata, "")) {
					
					smdatarecords.addRecords(smdata);
					logger.debug("copy hibernate to proto right");
					
				} else {
						logger.debug("copy error");
						//smactionbuilder.setErrMsg("copy error");
						//smactionbuilder.setStatus("false");
						return false;
				}	
		}
		return true;
	}
	

}
