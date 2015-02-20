package com.itu.dataserveraction;

import javax.ws.rs.Path;

import com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction;
import com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords;
import com.itu.dataserverlogic.FrontSmartMeterLogic;

@Path("/FrontSmartMeterAction")
public class FrontSmartMeterAction extends CommonProtoAction<SmartMeterDataAction,SmartMeterDataAction> {

	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		cmpLogic = new FrontSmartMeterLogic();
	}

	
}
