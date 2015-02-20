package com.itu.dataserveraction;

import javax.ws.rs.Path;

import com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords;
import com.itu.dataserverlogic.LocalServerLogic2;


@Path("/LocalServerAction2")
public class LocalServerAction2 extends CommonProtoAction2<SmartMeterDataRecords> {

	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		cmpLogic = new LocalServerLogic2();
	}
	
	

}
