package com.messengerapphiber.controller;

import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import com.messengerapphiber.service.MessageService;

@Path("/paramexample")

public class ParamExampleResource {
	
	MessageService msd = new MessageService();

	@GET
	@Path("/annotations")
	public String getMess(@MatrixParam("param") String val,@HeaderParam("head") String hType,
			@CookieParam("cookie") String coParam)  {
		
		//@FormParam used for jsp/html form
		
		//@bean param =. used instead of writing multiple params as args.create a new class and define the params like private @queryParam() int year
		
		return "Hello"+val + " "+hType ;
		
	}
	
	@GET
	@Path("/contextex")
	public String getMe(@Context UriInfo uriInfo,@Context HttpHeaders httpHeader)
	{
		String st = uriInfo.getAbsolutePath().toString();
		String cookies = httpHeader.getCookies().toString();
		return "hi"+st + cookies;
	}
	

}
