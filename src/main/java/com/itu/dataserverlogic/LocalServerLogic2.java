package com.itu.dataserverlogic;

import org.apache.log4j.Logger;

import com.itu.DAO.CloudCommandDao;
import com.itu.action.SmartMeterDataRecordsProtos.SmartMeterDataRecords;
import com.itu.util.ClassDeepCopy;
import com.itu.util.Log4jUtil;

public class LocalServerLogic2 extends CommonProtoLogic2<SmartMeterDataRecords> {

	Logger logger = Log4jUtil.getLogger(LocalServerLogic2.class);
	@Override
	public SmartMeterDataRecords executeActionBuffer(SmartMeterDataRecords t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String executeAction(SmartMeterDataRecords t) {
		if (null == t)
			return "none values";

		//logger.debug(String.format("post a new command, id:%d, param1:%d, param1:%d", cmd.getId(), cmd.getParam1(), cmd.getParam1()));

		//com.itu.bean.CloudCommand cmdbean = new com.itu.bean.CloudCommand();

		if (ClassDeepCopy.CopyProtoToBean(null, t, null)) {
			//cmdbean.setCommandId(10);
			if (CloudCommandDao.addNewCommand(null)) {
				return "true";
			} else {
				logger.debug("add new command error");
			}
		} else {
			logger.debug("copy error");
		}

		return "false";
	}

	@Override
	public String executeAction() {
		// TODO Auto-generated method stub
		return null;
	}


}
