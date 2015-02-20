package com.itu.dataserverlogic;

import java.util.List;

import org.apache.log4j.Logger;

import com.itu.DAO.CloudCommandDao;
import com.itu.DAO.DataAccess;
import com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction;
import com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecord;
import com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords;
import com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords.Builder;
import com.itu.bean.CloudCommand;
import com.itu.bean.SmartMeterData;
import com.itu.util.ClassDeepCopy;
import com.itu.util.DateUtils;
import com.itu.util.Log4jUtil;



public class FrontSmartMeterLogic extends CommonProtoLogic<SmartMeterDataAction,SmartMeterDataAction>{

	static Logger logger = Log4jUtil.getLogger(FrontSmartMeterLogic.class);
	@Override
	public SmartMeterDataAction executeAction(SmartMeterDataAction t) {
		// TODO Auto-generated method stub
		List<String> smieeeaddress =null;
		int starttime=0,endtime=0;
		
	
		if(t.getOperation().equals("search"))
		{
		    com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.Builder smactionbuilder = t.toBuilder();//com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.newBuilder()

			starttime= t.getStartTime();
			endtime  = t.getEndTime();
			/**
			 * search the dedacated smartmeters
			 */
			if(null != (smieeeaddress = t.getSmIeeeAddressList())){
				/**
				 * search dedicated the smartmeters
				 */
				String hql = "from SmartMeterData where checked = 0 order by timestamp";//
				List<SmartMeterData> list = DataAccess.searchOperation(hql);
				com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords.Builder  smdatarecords = com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords.newBuilder();
			    for (SmartMeterData hibernatesmdata : list) {
					 logger.info(String.format("localserver getting a new data energey is :%s",
							 hibernatesmdata.getEnergy())); 
					    com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecord.Builder smdata = null;
						if (ClassDeepCopy.CopyBeanToProto(hibernatesmdata, smdata, "Timestamp")) {
							
							smdatarecords.addSmartMeterDataRecord(smdata);
							
						} else {
								logger.debug("copy error");
								smactionbuilder.setErrMsg("copy error");
								smactionbuilder.setStatus("false");
								//return smactionbuilder.build();
						}	
				}
			    /**
			     * for the Action Protoc
			     */
			    smactionbuilder.setStatus("true");
				
			    return smactionbuilder.build();
		    		
			}else{
				/**
				 * search all the smartmeters
				 */
				String hql = "from SmartMeterData where checked = 0 order by timestamp";//
				List<SmartMeterData> list = DataAccess.searchOperation(hql);
				com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords.Builder  smdatarecords = com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords.newBuilder();
			    for (SmartMeterData hibernatesmdata : list) {
					 logger.info(String.format("localserver getting a new data energey is :%s",
							 hibernatesmdata.getEnergy())); 
					    com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecord.Builder smdata = null;
						if (ClassDeepCopy.CopyBeanToProto(hibernatesmdata, smdata, "Timestamp")) {
							
							smdatarecords.addSmartMeterDataRecord(smdata);
						} else {
								logger.debug("copy error");
								smactionbuilder.setErrMsg("copy error");
								smactionbuilder.setStatus("false");
								//return smactionbuilder.build();
						}	
				}
			    /**
			     * for the Action Protoc
			     */
			    smactionbuilder.setStatus("true");
				
			    return smactionbuilder.build();
		  }
		}
		
		return null;
	}

	@Override
	public SmartMeterDataAction executeAction() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
