package com.itu.dataserveraction;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import com.itu.dataserverlogic.FrontSmartMeterLogic;
import com.itu.util.Log4jUtil;

@Path("/FrontServerSmartMeterSearchAction")
public class FrontSmartMeterAction extends CommonProtoAction<FrontServerSmartMeterDataAction,FrontServerSmartMeterDataAction> {
	//Logger logger = Log4jUtil.getLogger(CommonAction.class);
	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		logger = Log4jUtil.getLogger(FrontSmartMeterAction.class);
		logger.debug("FrontSmartMeterAction comming here!");
		cmpLogic = new FrontSmartMeterLogic();
	}

	@GET
	@Path("/hello")
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("testing using hello");
		return "Hello Jersey";
	}
}
