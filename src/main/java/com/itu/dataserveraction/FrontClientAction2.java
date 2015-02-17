package com.itu.dataserveraction;

import javax.ws.rs.Path;

import com.itu.dataserverlogic.FrontClientLogic2;
import com.itu.myserver.CloudCommandProtos.CloudCommand;

@Path("/frontclientaction2")
public class FrontClientAction2 extends CommonProtoAciton2<CloudCommand> {

	@Override
	protected void initCommonProtoLogic() {
		cmpLogic = new FrontClientLogic2();
	}
}
