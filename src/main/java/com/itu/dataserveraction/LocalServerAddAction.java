package com.itu.dataserveraction;

import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.itu.action.LocalServerSmartMeterDataActionProtos.LocalServerSmartMeterDataAction;
import com.itu.action.ResultsProtos.Result;
import com.itu.dataserverlogic.LocalServerAddLogic;
import com.itu.util.Log4jUtil;

@Path("/LocalServerAddSmartMeterData")
public class LocalServerAddAction extends CommonProtoAction<LocalServerSmartMeterDataAction,Result>{
	Logger logger = Log4jUtil.getLogger(CommonAction.class);

	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		logger.debug("adding coming here");
		cmpLogic =  new LocalServerAddLogic();
	}

}
