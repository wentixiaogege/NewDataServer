package com.itu.dataserverlogic;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import antlr.collections.List;

import com.itu.DAO.DataAccess;
import com.itu.action.CommonEnum.OpterationType;
import com.itu.action.LocalServerSmartMeterDataActionProtos.LocalServerSmartMeterDataAction;
import com.itu.action.LocalServerSmartMeterDataRecordProtos.LocalServerSmartMeterDataRecord;
import com.itu.action.ResultsProtos.Result;
import com.itu.bean.SmartMeterData;
import com.itu.util.ClassDeepCopy;
import com.itu.util.Log4jUtil;

public class LocalServerAddLogic extends
		CommonProtoLogic<LocalServerSmartMeterDataAction, Result> {

	Logger logger = Log4jUtil.getLogger(LocalServerAddLogic.class);
	com.itu.action.ResultsProtos.Result.Builder resultbuilder = com.itu.action.ResultsProtos.Result
			.newBuilder();

	@Override
	public Result executeAction() {
		// TODO Auto-generated method stub
		return null;

	}

	@Override
	public Result executeAction(LocalServerSmartMeterDataAction t) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		logger.debug(t.getOperation());
		if (t.getOperation().equals(OpterationType.ADD)) {
			System.out.println("cominghere");
			int count = t.getRecordsCount();
			int index = 0;

			java.util.List<SmartMeterData> hibernatesmdatalist = new ArrayList<>();
			System.out.println(count);
			for (int i = 0; i < count; i++) {
				LocalServerSmartMeterDataRecord record = t.getRecords(i);

				SmartMeterData bean = new SmartMeterData();
				if (ClassDeepCopy.CopyProtoToBean(record, bean)) {
					logger.debug("copying ...");
				} else {
					logger.debug("copy error");
					resultbuilder.setRes("false");
					resultbuilder.setErrMsg("internal copy error");
				}
				hibernatesmdatalist.add(bean);
			}
//			while (count > 0) {
//				LocalServerSmartMeterDataRecord record = t.getRecords(index);
//
//				SmartMeterData bean = new SmartMeterData();
//				if (ClassDeepCopy.CopyProtoToBean(record, bean)) {
//					logger.debug("copying ...");
//				} else {
//					logger.debug("copy error");
//					resultbuilder.setRes("false");
//					resultbuilder.setErrMsg("internal copy error");
//				}
//				count--;
//				index++;
//				hibernatesmdatalist.add(bean);
//			}
			System.out.println("after setting here");
			logger.debug("after setting here");

			if (DataAccess.addOperation(hibernatesmdatalist)) {
				resultbuilder.setRes("true");
				logger.debug("add new data");
			} else {
				logger.debug("add new data error");
				resultbuilder.setErrMsg("add new data error");
				resultbuilder.setRes("false");
			}
			System.out.println("after adding here");
			logger.debug("add " + index + "sm records to database");

			return resultbuilder.build();
		}
		resultbuilder.setRes("false");
		resultbuilder.setErrMsg("checking t.getOperation().equals(add) error");
		return resultbuilder.build();
	}

}
