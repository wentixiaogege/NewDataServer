package com.itu.dataserveraction;

import java.util.List;
import java.util.Map.Entry;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;

import com.itu.util.Log4jUtil;

public abstract class CommonAciton<T, V> {
	Logger logger = Log4jUtil.getLogger(CommonAciton.class);

	protected com.itu.dataserverlogic.ICommonLogic<T, V> commonLogic;

	public CommonAciton() {
		initLogic();
	}

	protected abstract void initLogic();

	protected Response ResponseResult(Object result) {
		return Response.status(200).entity(result).build();
	}

}
