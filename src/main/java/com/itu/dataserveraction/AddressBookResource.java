package com.itu.dataserveraction;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;


import com.itu.util.Log4jUtil;

@Path("/addressbook")
public class AddressBookResource {
	Logger logger= Log4jUtil.getLogger(AddressBookResource.class);
	
	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String sayHello() {
		logger.debug("testing using hello");
		return "Hello Jersey";
	}
	

}
