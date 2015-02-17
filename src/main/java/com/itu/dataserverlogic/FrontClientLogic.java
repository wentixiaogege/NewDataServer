package com.itu.dataserverlogic;

import org.apache.log4j.Logger;

import com.itu.myserver.CloudCmdActionProtos.CloudCmdAction;
import com.itu.myserver.CloudCommandProtos.CloudCommand;
import com.itu.util.Log4jUtil;

public class FrontClientLogic extends CommonProtoLogic<CloudCmdAction, CloudCommand> {

	Logger logger = Log4jUtil.getLogger(FrontClientLogic.class);

	@Override
	public CloudCommand executeAction(CloudCmdAction t)
	{
		t.getInsertCmdsList().stream().forEach(x -> logger.debug(String.format("{id:%d, param1:%d, cood:%d}", x.getId(), x.getParam1(), x.getCoordinatorId())));
		CloudCommand insertCmd = t.getInsertCmds(0);
		// return null;frontend_getSmartMeterData
		logger.debug("insertCmd " + insertCmd.getId());
		return insertCmd;
	}

	@Override
	public CloudCommand executeAction() {
		// TODO Auto-generated method stub
		return null;
	}

}
