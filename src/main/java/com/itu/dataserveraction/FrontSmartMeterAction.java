package com.itu.dataserveraction;

import javax.ws.rs.Path;

import org.apache.log4j.Logger;

import com.itu.action.FrontServerSmartMeterDataActionProtos.FrontServerSmartMeterDataAction;
import com.itu.dataserverlogic.FrontSmartMeterLogic;
import com.itu.util.Log4jUtil;

@Path("/FrontSmartMeterAction")
public class FrontSmartMeterAction extends CommonProtoAction<FrontServerSmartMeterDataAction,FrontServerSmartMeterDataAction> {
	Logger logger = Log4jUtil.getLogger(CommonAction.class);
	@Override
	protected void initCommonProtoLogic() {
		// TODO Auto-generated method stub
		logger.debug("comming here!");
		cmpLogic = new FrontSmartMeterLogic();
	}

	
}
