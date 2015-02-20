package com.itu.dataserveraction;

import javax.ws.rs.Path;

import com.itu.action.CloudCmdActionProtos.CloudCmdAction;
import com.itu.action.CloudCommandProtos.CloudCommand;
import com.itu.dataserverlogic.FrontClientLogic;

@Path("/frontclientaction")
public class FrontClientAction extends CommonProtoAction<CloudCmdAction, CloudCommand> {

	@Override
	protected void initCommonProtoLogic() {
		cmpLogic = new FrontClientLogic();
	}

}
