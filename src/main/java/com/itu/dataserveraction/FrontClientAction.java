package com.itu.dataserveraction;

import javax.ws.rs.Path;

import com.itu.dataserverlogic.FrontClientLogic;
import com.itu.myserver.CloudCmdActionProtos.CloudCmdAction;
import com.itu.myserver.CloudCommandProtos.CloudCommand;

@Path("/frontclientaction")
public class FrontClientAction extends CommonProtoAciton<CloudCmdAction, CloudCommand> {

	@Override
	protected void initCommonProtoLogic() {
		cmpLogic = new FrontClientLogic();
	}

}
