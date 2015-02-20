package com.itu.dataserveraction;

import javax.ws.rs.Path;

import com.itu.action.CloudCommandProtos.CloudCommand;
import com.itu.dataserverlogic.FrontClientLogic2;

@Path("/frontclientaction2")
public class FrontClientAction2 extends CommonProtoAction2<CloudCommand> {

	@Override
	protected void initCommonProtoLogic() {
		cmpLogic = new FrontClientLogic2();
	}
}
