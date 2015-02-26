package com.itu.dataserveraction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.itu.action.LocalServerSmartMeterDataActionProtos.LocalServerSmartMeterDataAction;
import com.itu.action.ResultsProtos.Result;
import com.itu.dataserverlogic.LocalServerAddLogic;
import com.itu.util.Log4jUtil;

@Path("/LocalServerAddSmartMeterData")
public class LocalServerAddAction extends CommonProtoAction<LocalServerSmartMeterDataAction,Result>{
	//Logger logger = Log4jUtil.getLogger(LocalServerAddAction.class);

	
	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(LocalServerAddAction.class);
		cmpLogic =  new LocalServerAddLogic();
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("testing using hello");
		return "Hello Jersey";
	}
}
