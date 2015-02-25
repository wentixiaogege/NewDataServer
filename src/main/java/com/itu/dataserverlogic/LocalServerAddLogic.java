package com.itu.dataserverlogic;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.action.LocalServerSmartMeterDataActionProtos.LocalServerSmartMeterDataAction;
import com.itu.action.LocalServerSmartMeterDataRecordProtos.LocalServerSmartMeterDataRecord;
import com.itu.action.ResultsProtos.Result;
import com.itu.util.ClassDeepCopy;
import com.itu.util.Log4jUtil;
import com.itu.util.OperationType;

public class LocalServerAddLogic extends
		CommonProtoLogic<LocalServerSmartMeterDataAction, Result> {

	Logger logger = Log4jUtil.getLogger(LocalServerAddLogic.class);
    com.itu.action.ResultsProtos.Result.Builder resultbuilder = com.itu.action.ResultsProtos.Result.newBuilder();

	@Override
	public Result executeAction() {
		// TODO Auto-generated method stub
		return null;
	
	}

	@Override
	public Result executeAction(LocalServerSmartMeterDataAction t) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub
			if(t.getOperation().equals(OperationType.ADD))
			{
			    int count = t.getRecordsCount();
			    int index = 0;
			    //com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.Builder smactionbuilder = t.toBuilder();//com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.newBuilder()
			    com.itu.bean.SmartMeterData[] hibernatesmdatalist = new com.itu.bean.SmartMeterData[count];
			    LocalServerSmartMeterDataRecord[] smdatalist = new LocalServerSmartMeterDataRecord[count];
			    while(count>0)
			    {
			    	smdatalist[index] = t.getRecords(index);
			    	if (ClassDeepCopy.CopyProtoToBean(smdatalist[index], hibernatesmdatalist[index], "")) {
						//cmdbean.setCommandId(10);
			    	
					} else {
						logger.debug("copy error");
						resultbuilder.setRes("false");
						resultbuilder.setErrMsg("internal copy error");
						
						
					}
			    	count--;
			    	index++;
			    }
				if(DataAccess.addOperation(hibernatesmdatalist))
	    		{
	    			resultbuilder.setRes("true");
	    			logger.debug("add new data");
	    		} else {
					logger.debug("add new data error");
					resultbuilder.setErrMsg("add new data error");
					resultbuilder.setRes("false");
				}
			   
			    logger.debug("add "+index+"sm records to database");
				return resultbuilder.build();
			}
			resultbuilder.setRes("false");
			resultbuilder.setErrMsg("checking t.getOperation().equals(add) error");
			return resultbuilder.build();
	}
	
}


