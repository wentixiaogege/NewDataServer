package com.itu.dataserverlogic;

import org.apache.log4j.Logger;

import com.itu.DAO.DataAccess;
import com.itu.action.ResultsProtos.Result;
import com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction;
import com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecord;
import com.itu.util.ClassDeepCopy;
import com.itu.util.Log4jUtil;

public class LocalServerAddLogic extends
		CommonProtoLogic<SmartMeterDataAction, Result> {

	Logger logger = Log4jUtil.getLogger(LocalServerAddLogic.class);
    com.itu.action.ResultsProtos.Result.Builder resultbuilder = com.itu.action.ResultsProtos.Result.newBuilder();

	@Override
	public Result executeAction(SmartMeterDataAction t) {
		// TODO Auto-generated method stub
		if(t.getOperation().equals("add"))
		{
		    int count = t.getRecordsCount();
		    int index = 0;
		    //com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.Builder smactionbuilder = t.toBuilder();//com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction.newBuilder()
		    com.itu.bean.SmartMeterData[] hibernatesmdatalist = new com.itu.bean.SmartMeterData[count];
		    SmartMeterDataRecord[] smdatalist = new SmartMeterDataRecord[count];
		    while(count>0)
		    {
		    	smdatalist[index] = t.getRecords(index);
		    	if (ClassDeepCopy.CopyProtoToBean(smdatalist[index], hibernatesmdatalist[index], "Timestamp")) {
					//cmdbean.setCommandId(10);
		    		if(DataAccess.addOperation(hibernatesmdatalist))
		    		{
		    			resultbuilder.setRes("true");
		    		} else {
						logger.debug("add new data error");
						resultbuilder.setErrMsg("add new data error");
						resultbuilder.setRes("false");
					}
				} else {
					logger.debug("copy error");
					resultbuilder.setRes("false");
					resultbuilder.setErrMsg("internal copy error");
				}
		    	
		    }
		    logger.debug("add "+index+"sm records to database");
			return resultbuilder.build();
		}
		return resultbuilder.build();
	
	}

	@Override
	public Result executeAction() {
		// TODO Auto-generated method stub
		return null;
	
	}
	
}


