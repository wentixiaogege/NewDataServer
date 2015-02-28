package com.itu.dataserverlogic;

import java.util.List;

import com.itu.util.Log4jUtil;
import com.sun.istack.logging.Logger;


public class CommonLogic1 extends ICommonLogic<List<String>, String> {
	@Override
	public String executeAction(List<String> t) {
		return "Hello World! This is from service with T";
	}

	@Override
	public String executeAction() {
		return "Hello World! This is from service";
	}

}
