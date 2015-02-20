package com.itu.dataserveraction;

import javax.ws.rs.Path;

import com.itu.action.ResultsProtos.Result;
import com.itu.action.SmartMeterDataActionProtos.SmartMeterDataAction;
import com.itu.dataserverlogic.LocalServerAddLogic;

@Path("/LocalServerAdd")
public class LocalServerAddAction extends CommonProtoAction<SmartMeterDataAction,Result>{

	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		cmpLogic =  new LocalServerAddLogic();
	}

}
